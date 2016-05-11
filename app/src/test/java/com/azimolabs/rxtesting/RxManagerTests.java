package com.azimolabs.rxtesting;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.plugins.RxJavaTestPlugins;

import static junit.framework.Assert.assertEquals;

/**
 * Created by maciek on 25.03.2016.
 */
@Config(sdk = 18, manifest = "/src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class RxManagerTests {

    RxManager rxManager = new RxManager();

    @Before
    public void setUp() {
        RxJavaTestPlugins.setMainThreadScheduler();
    }

    @After
    public void tearDown() {
        RxJavaTestPlugins.resetJavaTestPlugins();
    }

    @Test
    public void testGettingTransformedNumbers() {
        rxManager.getTransformedNumbers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserverValidator());
    }

    @Test
    public void testGettingTransformedAndMoreFlatMappedNumbers() {
        rxManager.getTransformedNumberMoreFlatMapped()
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