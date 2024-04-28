package com.rm.android_fundamentals.topics.t2_appnavigation.s2_viewpager

import android.os.Bundle
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityHostingBinding

class HostingActivity : BaseActivity() {

    lateinit var binding: ActivityHostingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun getTitleToolbar(): String = "Hosting activity"
}