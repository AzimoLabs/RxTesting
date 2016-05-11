package com.azimolabs.rxtesting;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by maciek on 25.03.2016.
 */
public class TransformedRxManager {

    private Map<Integer, String> numbersMap = new HashMap<>();

    private RxTransformer rxTransformer;

    public TransformedRxManager(RxTransformer transformer) {
        rxTransformer = transformer;

        numbersMap.put(1, "One");
        numbersMap.put(2, "Two");
        numbersMap.put(3, "Three");
        numbersMap.put(4, "Four");
        numbersMap.put(5, "Five");
    }

    public Observable<Integer> getNumbers() {
        return Observable.from(numbersMap.keySet())
            .compose(rxTransformer.<Integer>subscribeOnIo())
            .compose(rxTransformer.<Integer>observeOnMain());
    }

    public Observable<String> getTransformedNumbers() {
        return getNumbers()
            .flatMap(new Func1<Integer, Observable<String>>() {
                @Override
                public Observable<String> call(Integer integer) {
                    return Observable.just(numbersMap.get(integer));
                }
            });
    }

    public Observable<String> getTransformedNumberMoreFlatMapped() {
        Observable<String> transformedNumbersObservable = getTransformedNumbers()
            .flatMap(new Func1<String, Observable<String>>() {
                @Override
                public Observable<String> call(String s) {
                    Observable observable = Observable.just(s + " addition1");
                    return decorateWithFlatMap(observable);
                }
            }).compose(rxTransformer.<String>subscribeOnIo())
            .compose(rxTransformer.<String>observeOnMain());

        return decorateWithFlatMap(transformedNumbersObservable);
    }

    private Observable<String> decorateWithFlatMap(Observable<String> observable) {
        return observable.flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just(s + " decorator");
            }
        }).compose(rxTransformer.<String>subscribeOnIo())
            .compose(rxTransformer.<String>observeOnMain());
    }
}