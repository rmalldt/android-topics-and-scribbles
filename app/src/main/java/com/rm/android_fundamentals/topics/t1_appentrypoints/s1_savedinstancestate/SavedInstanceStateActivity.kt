package com.rm.android_fundamentals.topics.t1_appentrypoints.s1_savedinstancestate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.rm.android_fundamentals.databinding.ActivitySavedInstanceBinding
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.utils.EMPTY_STRING

class SavedInstanceStateActivity : BaseActivity() {

    private var num = 0
    private lateinit var binding: ActivitySavedInstanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedInstanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add lifecycle observer
        lifecycle.addObserver(SavedInstanceStateActivityObserver(this))

        // Recover saved instance state
        if (savedInstanceState != null) {
            val savedInt = savedInstanceState.getInt(NUM_KEY, 10)
            num = savedInt
            binding.tvNum.text = num.toString()
        }

        binding.btnCounter.setOnClickListener {
            ++num
            binding.tvNum.text = num.toString()
        }

       binding.btnSubmit.setOnClickListener {
           binding.txtInfo.text = binding.txtTypeSomething.text
       }

        displayEditTextValueOnTyping(binding.txtTypeSomething, binding.txtInfo)
    }

    /**
     * Invoked when the activity is temporarily destroyed and the instance states
     * are saved.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.apply {
            putInt(NUM_KEY, num)
            putString(TEXT_KEY, binding.txtTypeSomething.text.toString())
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
        val savedText = savedInstanceState.getString(TEXT_KEY, EMPTY_STRING)
        binding.txtInfo.text = savedText
    }

    private fun displayEditTextValueOnTyping(editText: EditText, textView: TextView) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textView.text = s.toString()
            }
        })
    }

    override fun getTitleToolbar() = "SavedInstanceState activity"

    companion object {
        const val NUM_KEY = "numKey"
        const val TEXT_KEY = "textKey"
    }
}