package com.rm.android_fundamentals.topics.t3_architecturecomponents.s5_viewmodels.ex2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivitySavedStateHandleBinding
import kotlinx.coroutines.launch
import timber.log.Timber

class SavedStateHandleActivity : BaseActivity() {

    private lateinit var binding: ActivitySavedStateHandleBinding

    private val viewModel: SavedStateHandleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedStateHandleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.d("Activity created")

        binding.button.setOnClickListener { viewModel.incrementCounter() }

        lifecycleScope.launch {
            viewModel.counter.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    binding.tvSavedState.text = viewModel.counter.value.toString()
                }
        }
    }

    override fun getTitleToolbar(): String = "ViewModel: Saved state handle activity"
}