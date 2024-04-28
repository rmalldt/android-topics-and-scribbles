package com.rm.android_fundamentals.topics.t3_architecturecomponents.s4_coroutinescopes

import android.os.Bundle
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityCoroutineScopesBinding

class CoroutineScopesActivity : BaseActivity() {

    private lateinit var binding: ActivityCoroutineScopesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineScopesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun getTitleToolbar() = "Coroutine Scopes Activity"
}