package com.rm.android_fundamentals.topics.t1_appentrypoints.s1_savedinstancestate

import android.content.Intent
import android.os.Bundle
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.databinding.ActivitySavedInstanceBinding
import com.rm.android_fundamentals.topics.t6_viewlayouts.s1_recyclerview.RecyclerViewExActivity

class SavedInstanceStateActivity : BaseActivity() {

    private var num = 0
    private lateinit var binding: ActivitySavedInstanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedInstanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recover the instance state
        if (savedInstanceState != null) {
            val savedName = savedInstanceState.getString(NAME_KEY, "")
            val savedSubject = savedInstanceState.getString(SUBJECT_KEY, "")
            binding.numberTV.text = num.toString()
            val res = "$savedName, $savedSubject"
            binding.txtInfo.text = res
        }

        // Add lifecycle observer
        lifecycle.addObserver(SavedInstanceStateActivityObserver(this))

        // Click listeners
        binding.apply {
            btnCounter.setOnClickListener {
                ++num
                numberTV.text = num.toString()
            }

            btnSubmit.setOnClickListener {
                val res = "${txtInputName.text}, ${txtInputSubject.text}"
                txtInfo.text = res
            }
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, RecyclerViewExActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Invoked when the activity is temporarily destroyed and the instance states
     * are saved.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        val savedInt = num
        outState.apply {
            putInt(NUM_KEY, savedInt)
            putString(NAME_KEY, binding.txtInputName.text.toString())
            putString(SUBJECT_KEY, binding.txtInputSubject.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    /**
     * This callback is called only when there is a saved instance previously saved using
     * onSavedInstanceState().
     *  - States can be restored in onCreate()
     *  - Others can be optionally restored here.
     *
     * System calls this method after the onStart() method only if there's a
     * saved state to restore.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Get saved instance states
        val savedInt = savedInstanceState.getInt(NUM_KEY, 10)
        num = savedInt
    }

    override fun getTitleToolbar() = "SavedInstanceState activity"

    companion object {
        const val NUM_KEY = "savedInt"
        const val NAME_KEY = "savedName"
        const val SUBJECT_KEY = "savedSubject"
    }
}