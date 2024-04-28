package com.rm.android_fundamentals.topics.t3_architecturecomponents.s1_lifecycle.withoutlifecyclecomponent

import android.os.Bundle
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityLifecycleWithoutBinding
import com.rm.android_fundamentals.utils.toast

class WithoutLifeCycleComponentActivity : BaseActivity() {

    private lateinit var binding: ActivityLifecycleWithoutBinding

    private lateinit var myListener: MyListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLifecycleWithoutBinding.inflate(layoutInflater)

        setContentView(binding.root)

        myListener = MyListener(this) { string ->
            this@WithoutLifeCycleComponentActivity.toast("Message: $string")
        }
    }

    override fun onStart() {
        super.onStart()
        myListener.start()
    }

    override fun onStop() {
        super.onStop()
        myListener.stop()
    }

    override fun getTitleToolbar() = "Without lifecycle-aware component Activity"
}