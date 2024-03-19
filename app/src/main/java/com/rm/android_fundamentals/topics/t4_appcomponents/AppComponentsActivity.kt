package com.rm.android_fundamentals.topics.t4_appcomponents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityAppComponentsBinding
import com.rm.android_fundamentals.databinding.ActivityArchitectureComponentsBinding

class AppComponentsActivity : BaseActivity() {

    private lateinit var binding: ActivityAppComponentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppComponentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun getTitleToolbar() = "App Components Activity"
}