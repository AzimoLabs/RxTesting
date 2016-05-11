package com.azimolabs.rxtesting;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by maciek on 25.03.2016.
 */
public class RxTransformer {

    public <T> Observable.Transformer<T, T> subscribeOnIo() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io());
            }
        };
    }

    public <T> Observable.Transformer<T, T> observeOnMain() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
