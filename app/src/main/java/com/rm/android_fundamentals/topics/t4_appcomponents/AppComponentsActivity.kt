package com.rm.android_fundamentals.topics.t4_appcomponents

import android.os.Bundle
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityAppComponentsBinding

class AppComponentsActivity : BaseActivity() {

    private lateinit var binding: ActivityAppComponentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppComponentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun getTitleToolbar() = "App Components Activity"
}