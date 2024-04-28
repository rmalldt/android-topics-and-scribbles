package com.rm.android_fundamentals.topics.t1_appentrypoints.s2_passdatabetweenactivities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityResultProducingBinding
import com.rm.android_fundamentals.utils.toast

class ResultProducingActivity : BaseActivity() {

    private lateinit var binding: ActivityResultProducingBinding
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultProducingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        setUp()
    }

    /**
     * Get data from ResultActivity
     */
    private fun getData() {
        val bundle = intent.extras

        bundle?.let {
            // If object was sent
            val myObject = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("OBJ", MyObject::class.java)
            } else {
                bundle.getParcelable("OBJ")
            }
            myObject?.let {
                this@ResultProducingActivity.toast("MyObject(id: ${it.id}, name: ${it.name})")
                return
            }

            // If data was sent
            val data1 = bundle.getString("Data1")
            val date2 = bundle.getString("Data2")
            this@ResultProducingActivity.toast("Date1: $data1, Data2: $date2")
        }
    }

    /**
     * Returns the result expected by the activity that started this activity.
     * The Intent object is setup by the calling activity with the key-value
     * inputs which this activity can process and return the result.
     */
    private fun setUp() {
        binding.apply {
            btnIncrement.setOnClickListener {
                ++count
                txtNumber.text = count.toString()
            }

            btnReturnResult.setOnClickListener {
                if (intent.getStringExtra(ResultActivity.RESULT_KEY) == "test") {
                    val intent = Intent()
                    intent.putExtra(ResultActivity.RESULT_KEY, count)
                    setResult(RESULT_OK, intent)
                }
                finish()
            }
        }
    }

    override fun getTitleToolbar() = "Post Activity"
}
