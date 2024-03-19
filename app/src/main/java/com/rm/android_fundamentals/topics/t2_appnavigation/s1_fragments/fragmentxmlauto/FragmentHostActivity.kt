package com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments.fragmentxmlauto

import android.os.Bundle
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityFragmentHostBinding

class FragmentHostActivity : BaseActivity() {

    lateinit var binding: ActivityFragmentHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sendData()
    }

    private fun sendData() {
        val bundle = Bundle()
        bundle.putString("data", "From Activity")
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        fragment?.arguments = bundle
    }

    override fun getTitleToolbar(): String = "Fragments Auto Activity"
}