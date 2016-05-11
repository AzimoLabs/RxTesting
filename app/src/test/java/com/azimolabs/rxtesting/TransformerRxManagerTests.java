package com.azimolabs.rxtesting;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

import static junit.framework.Assert.assertEquals;

/**
 * Created by maciek on 25.03.2016.
 */
@Config(sdk = 18, manifest = "/src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class TransformerRxManagerTests {

    TransformedRxManager transformedRxManager;

    RxTransformer mockTransformer = new RxTransformer() {

        @Override
        public <T> Observable.Transformer<T, T> subscribeOnIo() {
            return new Observable.Transformer<T, T>() {
                @Override
                public Observable<T> call(Observable<T> observable) {
                    return observable;
                }
            };
        }

        @Override
        public <T> Observable.Transformer<T, T> observeOnMain() {
            return new Observable.Transformer<T, T>() {
                @Override
                public Observable<T> call(Observable<T> observable) {
                    return observable;
                }
            };
        }

    };

    @Before
    public void setUp() {
        transformedRxManager = new TransformedRxManager(mockTransformer);
    }

    @Test
    public void testGettingTransformedNumbers() {
        transformedRxManager.getTransformedNumbers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserverValidator());
    }

    @Test
    public void testGettingTransformedAndMoreFlatMappedNumbers() {
        transformedRxManager.getTransformedNumberMoreFlatMapped()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserverValidator());
    }

    private Observer<String> getObserverValidator() {
        return new Observer<String>() {

            int counter;

            @Override
            public void onCompleted() {
                System.out.println("onCompleted, current thread: " + Thread.currentThread());
                assertEquals(counter, 5);
            }

            @Override
            public void onError(Throwable e) {
                Assert.fail();
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext, current thread: " + Thread.currentThread());
                counter++;
            }

        };
    }
}