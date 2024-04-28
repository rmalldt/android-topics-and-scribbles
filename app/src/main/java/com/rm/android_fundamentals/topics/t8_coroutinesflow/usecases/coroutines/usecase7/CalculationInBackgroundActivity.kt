package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase7

import android.os.Bundle
import androidx.activity.viewModels
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityCalculationInBackgroundBinding
import com.rm.android_fundamentals.topics.t8_coroutinesflow.base.useCase7Description
import com.rm.android_fundamentals.utils.EMPTY_STRING
import com.rm.android_fundamentals.utils.hideKeyboard
import com.rm.android_fundamentals.utils.setGone
import com.rm.android_fundamentals.utils.setVisible
import com.rm.android_fundamentals.utils.toast

class CalculationInBackgroundActivity : BaseActivity() {

    private val binding by lazy {
        ActivityCalculationInBackgroundBinding.inflate(layoutInflater)
    }

    private val viewModel: CalculationInBackgroundViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.uiState.observe(this){ uiState ->
            if (uiState != null) {
                render(uiState)
            }
        }

        binding.btnCalculate.setOnClickListener {
            val factorialOf = binding.editTxtFactorialOf.text.toString().toIntOrNull()
            val noOfCoroutines = binding.editTxtNumOfCoroutines.text.toString().toIntOrNull()
            if (factorialOf != null && noOfCoroutines != null) {
                viewModel.performCalculation(factorialOf, noOfCoroutines)
            }
        }
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> onLoad()
            is UiState.Success -> onSuccess(uiState)
            is UiState.Error -> onError(uiState)
        }
    }

    private fun onLoad() = with(binding) {
        progressBar.setVisible()
        tvResult.text = EMPTY_STRING
        tvCalculationDuration.text = EMPTY_STRING
        tvStringConversionDuration.text = EMPTY_STRING
        btnCalculate.isEnabled = false
        tvResult.hideKeyboard()
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        progressBar.setGone()
        btnCalculate.isEnabled = true
        tvCalculationDuration.text =
            getString(R.string.duration_calculation, uiState.computationDuration)

        tvStringConversionDuration.text =
            getString(R.string.duration_string_conversion, uiState.stringConversionDuration)

        tvResult.text = if (uiState.result.length <= 150) uiState.result else "${uiState.result.substring(0, 147)}..."
    }

    private fun onError(uiState: UiState.Error) = with(binding) {
        progressBar.setGone()
        btnCalculate.isEnabled = true
        toast(uiState.message)
    }

    override fun getTitleToolbar() = useCase7Description
}