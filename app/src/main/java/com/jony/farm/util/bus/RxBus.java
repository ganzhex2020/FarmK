package com.jony.farm.util.bus;

import io.reactivex.Observable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {
    //    // 有背压处理的 RxBus
      //  private final FlowableProcessor<Object> bus;
    //非背压处理
    private final Subject<Object> bus;
    private static volatile RxBus defaultRxBus;

    private RxBus() {
        //非背压处理
        bus = PublishSubject.create().toSerialized();

        //        // 有背压处理的 RxBus
        //        bus = PublishProcessor.create().toSerialized();

    }

    public static RxBus getInstance() {
        if (null == defaultRxBus) {
            synchronized (RxBus.class) {
                if (null == defaultRxBus) {
                    defaultRxBus = new RxBus();
                }
            }
        }
        return defaultRxBus;
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    public boolean hasObservable() {
        return bus.hasObservers();
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    /*
     * 转换为特定类型的Obserbale
     */
    public <T> Observable<T> toObservable(Class<T> type) {
        return bus.ofType(type);
    }
}
