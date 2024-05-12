package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase2

import com.rm.android_fundamentals.extensions.InstantExecutorExtension
import com.rm.android_fundamentals.extensions.MainDispatcherExtension
import com.rm.android_fundamentals.mocknetwork.mock.MockApi
import com.rm.android_fundamentals.mocknetwork.mock.mockAndroidVersions
import com.rm.android_fundamentals.mocknetwork.mock.mockVersionFeaturesAndroid10
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class, MainDispatcherExtension::class)
class SequentialNetworkRequestsViewModelTest {

    private val mockApi: MockApi = mockk()
    private lateinit var viewModel: SequentialNetworkRequestsViewModel

    private val receivedStates = mutableListOf<UiState>()

    @BeforeEach
    fun setUp() {
        viewModel = SequentialNetworkRequestsViewModel(mockApi)
    }

    @Test
    fun `performSingleNetwork should return Success when network request is successful`() = runTest {
        // GIVEN
        coEvery { mockApi.getRecentAndroidVersions() } returns mockAndroidVersions
        coEvery { mockApi.getAndroidVersionFeatures(LATEST_API_LEVEL) } returns mockVersionFeaturesAndroid10

        // WHEN
        observeViewModel(viewModel)
        viewModel.performSequentialNetworkRequests()

        // THEN
        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockVersionFeaturesAndroid10),
            ),
            receivedStates
        )
    }

    @Test
    fun `performSingleNetwork should return Error when network request fails`() = runTest {
        // GIVEN
        coEvery { mockApi.getRecentAndroidVersions() } throws RuntimeException()
        coEvery { mockApi.getAndroidVersionFeatures(LATEST_API_LEVEL) } returns mockVersionFeaturesAndroid10

        // WHEN
        observeViewModel(viewModel)
        viewModel.performSequentialNetworkRequests()

        // THEN
        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error(ERROR_MESSAGE),
            ),
            receivedStates
        )
    }

    private fun observeViewModel(viewModel: SequentialNetworkRequestsViewModel) {
        viewModel.uiState.observeForever { uiState ->
            receivedStates.add(uiState)
        }
    }

    companion object {
        const val LATEST_API_LEVEL = 29
        const val ERROR_MESSAGE = "Network Request failed!"
    }
}