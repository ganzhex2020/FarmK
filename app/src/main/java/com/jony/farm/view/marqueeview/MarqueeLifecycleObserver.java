package com.jony.farm.view.marqueeview;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public interface MarqueeLifecycleObserver extends LifecycleObserver {

    void onStop(LifecycleOwner owner);

    void onStart(LifecycleOwner owner);

    void onDestroy(LifecycleOwner owner);
}
