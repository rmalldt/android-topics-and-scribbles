package com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments.fragmentprogrammatic.OneFragment
import com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments.fragmentprogrammatic.ThreeFragment
import com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments.fragmentprogrammatic.TwoFragment

class MyFragmentFactory : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            OneFragment::class.java -> OneFragment()
            TwoFragment::class.java -> TwoFragment()
            ThreeFragment::class.java -> ThreeFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
