package com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments.fragmentprogrammatic

import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityFragmentHostManualBinding

class FragmentHostManualActivity : BaseActivity() {

    lateinit var binding: ActivityFragmentHostManualBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentHostManualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //supportFragmentManager.fragmentFactory = MyFragmentFactory()

        // Set initial fragment
        if (savedInstanceState == null) {
            setInitialFragment()
        }

        // Set initial fragment backstack
        binding.txtBackStackCount.text = (supportFragmentManager.backStackEntryCount).toString()

        // Update backstack count on back stack change
        supportFragmentManager.addOnBackStackChangedListener {
            binding.txtBackStackCount.text = (supportFragmentManager.backStackEntryCount).toString()
        }

        // Add fragment
        binding.btnAddFragment.setOnClickListener { addFragmentInCircle() }

    }

    private fun setInitialFragment() {
        // Programmatically create add fragment to this Activity
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            val bundle = Bundle()
            bundle.putString("key", "MyData")
            add<OneFragment>(R.id.fragment_container, args = bundle) // pass data to child fragment
            addToBackStack(null) // name can be null and back stack records the transaction
        }
    }

    private fun addFragmentInCircle() {
        var fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        fragment = when (fragment) {
            is OneFragment -> TwoFragment()
            is TwoFragment -> ThreeFragment()
            is ThreeFragment -> OneFragment()
            else -> OneFragment()
        }

        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.from_right, // enter
                R.anim.to_left,    // exit
                R.anim.from_left,  // popEnter
                R.anim.to_right    // popExit
            )
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun getTitleToolbar(): String = "Fragments Programmatically Activity"
}