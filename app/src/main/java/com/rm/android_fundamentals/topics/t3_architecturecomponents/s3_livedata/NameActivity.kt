package com.rm.android_fundamentals.topics.t3_architecturecomponents.s3_livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityNameBinding
import kotlinx.coroutines.launch

class NameActivity : BaseActivity() {

    private lateinit var binding: ActivityNameBinding
    private val nameRepository: NameRepository = NameRepository()

    private val viewModel: NameViewModel by viewModels {
        NameViewModel.Factory(nameRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()

        binding.btnChangeName.setOnClickListener {
            viewModel.updateName()
        }
    }

    private fun observeData() {
        // Create the observer which update the UI
        val nameObserver = Observer<String> { newName ->
            binding.txtName.text = newName
        }

        // Observe the LiveData passing in this activity as the LifecycleOwner and the Observer
        viewModel.currentName.observe(this, nameObserver)
    }

    override fun getTitleToolbar() = "LiveData: Name Activity"
}