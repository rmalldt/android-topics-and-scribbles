package com.rm.android_fundamentals.topics.t3_passdatabetweenactivities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityResultBinding
import com.rm.android_fundamentals.utils.toastMessage

class ResultActivity : BaseActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startForResult()
        passData()
    }


    private fun startForResult() {
        // Using generic ActivityResultContract
        // In this case, the input is Intent
        binding.btnResult1.setOnClickListener {
            val intent = Intent(this, ResultProducingActivity::class.java)
            intent.putExtra(RESULT_KEY, "testInput") // input to start the ResultProducingActivity
            genericContractResultLauncher.launch(intent)
        }

        // Using custom ActivityResultContract
        // In this case, the input is String
        binding.btnResult2.setOnClickListener {
            customContractResultLauncher.launch("testInput") // input to start the ResultProducingActivity
        }
    }

    /**
     * The registerForActivityResult() takes:
     *  - a generic contract class StartActivityForResult.
     *  - a lambda to process the returned result; in case of generic contract,
     *    lambda returns Intent object from where the result is extracted for processing.
     * And returns ActivityResultLauncher.
     */
    private val genericContractResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            intent?.let {
                toastMessage(applicationContext, "Count: ${it.getIntExtra(RESULT_KEY, 0)}")
            }
        }
    }

    /**
     * The registerForActivityResult() takes:
     *  - a custom contract class CustomActivityContract.
     *  - a lambda to process the returned result; in case of custom contract,
     *    lambda returns the object type specified in the custom contract.
     *    In this example, it returns Int?.
     * And returns ActivityResultLauncher.
     */
    private val customContractResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(CustomActivityContract()) { result: Int? ->
        if (result != 0) toastMessage(applicationContext, "Result: $result")
        else toastMessage(applicationContext, "Result: $result")
    }

    /**
     * Pass data to ResultProducingActivity.
     */
    private fun passData() {
        binding.btnPassData.setOnClickListener {
            val data1 = binding.btnResult1.text.toString()
            val data2 = binding.btnResult2.text.toString()
            prepareData(data1, data2)
        }

        binding.btnPassObject.setOnClickListener {
            val myObject = MyObject(10, "MyParcelableObject")
            prepareData(myObject)
        }
    }

    private fun <T> prepareData(vararg dataList: T) {
        val intent = Intent(this, ResultProducingActivity::class.java)
        val bundle = Bundle()

        for (i in dataList.indices) {
            if (dataList[i] is String) {
                bundle.putString("Data${i+1}", dataList[i] as String)
            }

            if (dataList[i] is MyObject) {
                bundle.putParcelable("OBJ", dataList[i] as MyObject)
            }
        }
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun getTitleToolbar() = "Result Activity"

    companion object {
        val RESULT_KEY = "RESULT_KEY"
    }
}