package com.rm.android_fundamentals.topics.t2_savedinstancestate

import android.os.Bundle
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.databinding.ActivitySavedInstanceBinding

class SavedInstanceStateCounterActivity : BaseActivity() {

    private var num = 0
    private lateinit var binding: ActivitySavedInstanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedInstanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val savedInt = num
        // Save instance states
        outState.putInt(NUM_KEY, savedInt)
        outState.putString(NAME_KEY, binding.txtInputName.text.toString())
        outState.putString(SUBJECT_KEY, binding.txtInputSubject.text.toString())
    }

    /**
     * System calls this method after the onStart() method only if there's a
     * saved state to restore.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Get saved instance states
        val savedInt = savedInstanceState.getInt(NUM_KEY, 0)
        val savedName = savedInstanceState.getString(NAME_KEY, "")
        val savedSubject = savedInstanceState.getString(SUBJECT_KEY, "")
        num = savedInt
        binding.numberTV.text = num.toString()
        val res = "$savedName, $savedSubject"
        binding.txtInfo.text = res
    }

    override fun getTitleToolbar() = "Android fundamentals"

    companion object {
        val NUM_KEY = "savedInt"
        val NAME_KEY = "savedName"
        val SUBJECT_KEY = "savedSubject"
    }
}