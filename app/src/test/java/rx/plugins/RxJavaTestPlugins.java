package rx.plugins;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by maciek on 25.03.2016.
 */
public class RxJavaTestPlugins {

    private static Scheduler immediateScheduler;

    private static RxJavaSchedulersHook javaImmediateHook = new RxJavaSchedulersHook() {
        @Override
        public Scheduler getIOScheduler() {
            return getImmediateScheduler();
        }

        @Override
        public Scheduler getComputationScheduler() {
            return getImmediateScheduler();
        }

        @Override
        public Scheduler getNewThreadScheduler() {
            return getImmediateScheduler();
        }
    };

    private static RxJavaSchedulersHook javaMainThreadHook = new RxJavaSchedulersHook() {
        @Override
        public Scheduler getIOScheduler() {
            return AndroidSchedulers.mainThread();
        }

        @Override
        public Scheduler getComputationScheduler() {
            return AndroidSchedulers.mainThread();
        }

        @Override
        public Scheduler getNewThreadScheduler() {
            return AndroidSchedulers.mainThread();
        }
    };

    public static void resetJavaTestPlugins() {
        RxJavaPlugins.getInstance().reset();
    }

    public static void setImmediateScheduler() {
        RxJavaPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().registerSchedulersHook(javaImmediateHook);
    }

    public static void setMainThreadScheduler() {
        RxJavaPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().registerSchedulersHook(javaMainThreadHook);
    }

    private static Scheduler getImmediateScheduler() {
        if (immediateScheduler == null) {
            immediateScheduler = Schedulers.immediate();
        }
        return immediateScheduler;
    }

}