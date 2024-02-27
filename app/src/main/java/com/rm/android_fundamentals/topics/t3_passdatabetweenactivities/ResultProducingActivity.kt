package com.rm.android_fundamentals.topics.t3_passdatabetweenactivities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityResultProducingBinding
import com.rm.android_fundamentals.utils.toastMessage

class ResultProducingActivity : BaseActivity() {

    private var count = 0
    private lateinit var binding: ActivityResultProducingBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultProducingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
        getData()
    }

    /**
     * Return the result expected by the activity that started this activity.
     *  - The Intent object is setup by the calling activity with the key-value
     *    inputs which this activity can process and return the result.
     */
    private fun setUp() {
        binding.apply {
            btnIncrement.setOnClickListener {
                ++count
                txtNumber.text = count.toString()
            }

            btnReturnResult.setOnClickListener {
                if (intent.getStringExtra(ResultActivity.RESULT_KEY) == "testInput") {
                    val intent = Intent()
                    intent.putExtra(ResultActivity.RESULT_KEY, count)
                    setResult(RESULT_OK, intent)
                }
                finish()
            }
        }
    }

    /**
     * Get data from ResultActivity
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getData() {
        val bundle = intent.extras

        bundle?.let {
            // If object was sent
            val myObject = bundle.getParcelable("OBJ", MyObject::class.java)
            myObject?.let {
                toastMessage(this@ResultProducingActivity, "MyObject(id: ${it.id}, name: ${it.name})")
                return
            }

            // If data was sent
            val data1 = bundle.getString("Data1")
            val date2 = bundle.getString("Data2")
            toastMessage(this@ResultProducingActivity, "Date1: $data1, Data2: $date2")
        }
    }

    override fun getTitleToolbar() = "Post Activity"
}
