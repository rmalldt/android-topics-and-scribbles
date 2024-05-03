package com.rm.android_fundamentals.topics.t1_appentrypoints.s2_passdatabetweenactivities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityResultBinding
import com.rm.android_fundamentals.utils.toast
import timber.log.Timber

class ResultActivity : BaseActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passData()

        startForResult()
    }

    /**
     * Pass data to ResultProducingActivity.
     */
    private fun passData() {
        binding.btnPassObject.setOnClickListener {
            val text = binding.editTxtInput.text.toString()
            if (text.isNotEmpty()) {
                val myObject = MyObject(10, text)
                val bundle = Bundle()
                bundle.putParcelable("obj", myObject)

                val intent = Intent(this, ResultProducingActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                toast("Please type something")
            }
        }
    }

    private fun startForResult() {
        // Uses generic ActivityResultContract, in this case, the input type is Intent
        binding.btnResult1.setOnClickListener {
            val intent = Intent(this, ResultProducingActivity::class.java)
            intent.putExtra(RESULT_KEY, "test")
            genericContractResultLauncher.launch(intent)
        }

        // Uses generic ActivityResultContract, in this case, the input type is String
        binding.btnResult2.setOnClickListener {
            customContractResultLauncher.launch("test")
        }
    }

    /**
     * The registerForActivityResult() takes:
     * - a generic contract class ActivityResultContracts<Intent, ActivityResult>, where
     *   input type = Intent and output type = ActivityResult.
     * - a lambda with parameter ActivityResult to use the returned result.
     *
     * Returns ActivityResultLauncher.
     */
    private val genericContractResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            intent?.let {
                this@ResultActivity.toast("Count: ${it.getIntExtra(RESULT_KEY, 0)}")
            }
        }
    }

    /**
     * The registerForActivityResult() takes:
     *  - a custom contract class CustomActivityContract<String, Int?>, where
     *    input type = String and output type = Int?
     *  - a lambda with parameter Int? to use the returned result.
     *
     * Returns ActivityResultLauncher.
     */
    private val customContractResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(CustomActivityContract()) { result: Int? ->
        if (result != 0) this@ResultActivity.toast("Result: $result")
        else this@ResultActivity.toast("Result: $result")
    }

    override fun getTitleToolbar() = "Result Activity"

    companion object {
        const val RESULT_KEY = "RESULT_KEY"
    }
}
