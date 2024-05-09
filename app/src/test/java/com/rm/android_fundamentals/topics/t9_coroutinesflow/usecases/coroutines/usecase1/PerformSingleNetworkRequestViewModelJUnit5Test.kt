package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase1

import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.MockApi
import com.rm.android_fundamentals.topics.t9_coroutinesflow.mock.mockAndroidVersions
import com.rm.android_fundamentals.extensions.InstantExecutorExtension
import com.rm.android_fundamentals.extensions.MainDispatcherExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.lang.RuntimeException

@ExtendWith(InstantExecutorExtension::class, MainDispatcherExtension::class)
class PerformSingleNetworkRequestViewModelJUnit5Test {

    private val mockApi: MockApi = mockk()
    private lateinit var viewModel: PerformSingleNetworkRequestViewModel

    private val receivedStates = mutableListOf<UiState>()

    @BeforeEach
    fun setUp() {
        viewModel = PerformSingleNetworkRequestViewModel(mockApi)
    }

    @Test
    fun `performSingleNetwork should return Success when network request is successful`() = runTest {
        // GIVEN
        coEvery { mockApi.getRecentAndroidVersions() } returns mockAndroidVersions

        // WHEN
        observeViewModel(viewModel)
        viewModel.performSingleNetworkRequest()

        // THEN
        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockAndroidVersions),
            ),
            receivedStates
        )
    }

    @Test
    fun `performSingleNetwork should return Error when network request fails`() = runTest {
        // GIVEN
        coEvery { mockApi.getRecentAndroidVersions() } throws RuntimeException()

        // WHEN
        observeViewModel(viewModel)
        viewModel.performSingleNetworkRequest()

        // THEN
        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error(ERROR_MESSAGE),
            ),
            receivedStates
        )
    }

    @Test
    fun `uiState value is updated with success value when network request is successful`() = runTest {
        coEvery { mockApi.getRecentAndroidVersions() } returns mockAndroidVersions

        viewModel.performSingleNetworkRequest()

        assertEquals(viewModel.uiState.value, UiState.Success(mockAndroidVersions))
    }

    @Test
    fun `tate value is updated with error value when network request is successful`() = runTest {
        coEvery { mockApi.getRecentAndroidVersions() } throws RuntimeException()

        viewModel.performSingleNetworkRequest()

        assertEquals(viewModel.uiState.value, UiState.Error(ERROR_MESSAGE))
    }

    private fun observeViewModel(viewModel: PerformSingleNetworkRequestViewModel) {
        viewModel.uiState.observeForever { uiState ->
            receivedStates.add(uiState)
        }
    }

    companion object {
        const val ERROR_MESSAGE = "Network Request failed!"
    }
}