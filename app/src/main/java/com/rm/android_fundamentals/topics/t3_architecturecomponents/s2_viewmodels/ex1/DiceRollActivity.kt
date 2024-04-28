package com.rm.android_fundamentals.topics.t3_architecturecomponents.s2_viewmodels.ex1

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityDiceRollBinding
import kotlinx.coroutines.launch

class DiceRollActivity : BaseActivity() {

    lateinit var binding: ActivityDiceRollBinding

    private val viewModel: DiceRollViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiceRollBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeState()
        rollDice()
    }

    private fun observeState() {
        // Create a new coroutine in the lifecycleScope
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine everytime
            // the lifecycle is in the STARTED state (or above) and cancels it
            // when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // This happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                viewModel.uiState.collect { state ->
                    binding.apply {
                        txtRolls.text = state.numOfRolls.toString()
                        txtDie1.text = state.firstDieVal.toString()
                        txtDie2.text = state.secondDieVal.toString()
                    }
                }
            }
        }
    }

    private fun rollDice() {
        binding.btnRoll.setOnClickListener {
            viewModel.rollDice()
        }
    }

    override fun getTitleToolbar() = "ViewModel: DiceRoll Activity"
}