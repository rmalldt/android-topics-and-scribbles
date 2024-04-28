package com.rm.android_fundamentals.topics.t1_appentrypoints.s1_savedinstancestate

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.utils.toast

/**
 * Observers the lifecycle of [SavedInstanceStateActivity]
 */
class SavedInstanceStateActivityObserver(private val baseActivity: BaseActivity) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        baseActivity.toast("OnResumed called")
    }
}