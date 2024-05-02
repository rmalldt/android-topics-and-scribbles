package com.rm.android_fundamentals.topics.t3_architecturecomponents.s1_lifecycle.withlifecyclecomponent

import android.os.Bundle
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityLifecycleBinding
import com.rm.android_fundamentals.utils.toast

class LifecycleActivity : BaseActivity() {

    private lateinit var binding: ActivityLifecycleBinding
    private lateinit var myObserver: MyLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLifecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addObserver()

        checkState()
    }

    // Add lifecycle observer
    private fun addObserver() {
        myObserver = MyLifecycleObserver(this, lifecycle) { string ->
            this@LifecycleActivity.toast(string)
        }
        lifecycle.addObserver(myObserver)
    }

    private fun checkState() {
        if (checkStatus()) {
            val state = myObserver.enabled()
            binding.tvInfo.text = state
        }
    }

    override fun getTitleToolbar() = "With lifecycle-aware component Activity"
}

fun checkStatus() = true