package com.rm.android_fundamentals.topics.t3_architecturecomponents.s5_viewmodels.ex1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class DiceRollViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DiceUiState())
    val uiState: StateFlow<DiceUiState> = _uiState.asStateFlow()

    fun rollDice() {
        _uiState.update { currentState ->
            currentState.copy(
                firstDieVal = Random.nextInt(1, 7),
                secondDieVal = Random.nextInt(1, 7),
                numOfRolls = currentState.numOfRolls + 1
            )
        }
    }
}

data class DiceUiState(
    val firstDieVal: Int? = null,
    val secondDieVal: Int? = null,
    val numOfRolls: Int = 0
)