package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.coroutines.usecase3

import com.rm.android_fundamentals.extensions.InstantExecutorExtension
import com.rm.android_fundamentals.extensions.MainDispatcherExtension
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.MockApi
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockAndroidVersions
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockVersionFeaturesAndroid10
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockVersionFeaturesOreo
import com.rm.android_fundamentals.topics.t8_coroutinesflow.mock.mockVersionFeaturesPie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class, MainDispatcherExtension::class)
class PerformConcurrentNetworkRequestsViewModelTest {

    private val mockApi: MockApi = mockk()
    private lateinit var viewModel: PerformConcurrentNetworkRequestsViewModel

    private val receivedStates = mutableListOf<UiState>()

    @BeforeEach
    fun setUp() {
        viewModel = PerformConcurrentNetworkRequestsViewModel(mockApi)
    }

    @Test
    fun performSequentialNetworkRequests()  = runTest {
        coEvery { mockApi.getRecentAndroidVersions() } returns mockAndroidVersions
        coEvery { mockApi.getAndroidVersionFeatures(27) } returns mockVersionFeaturesOreo
        coEvery { mockApi.getAndroidVersionFeatures(28) } returns mockVersionFeaturesPie
        coEvery { mockApi.getAndroidVersionFeatures(29) } returns mockVersionFeaturesAndroid10

        observeViewModel(viewModel)
        viewModel.performSequentialNetworkRequests()

        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(
                    listOf(
                        mockVersionFeaturesOreo,
                        mockVersionFeaturesPie,
                        mockVersionFeaturesAndroid10
                    )
                ),
            ),
            receivedStates
        )
    }

    @Test
    fun performConcurrentNetworkRequests() = runTest {
        coEvery { mockApi.getRecentAndroidVersions() } returns mockAndroidVersions
        coEvery { mockApi.getAndroidVersionFeatures(27) } returns mockVersionFeaturesOreo
        coEvery { mockApi.getAndroidVersionFeatures(28) } returns mockVersionFeaturesPie
        coEvery { mockApi.getAndroidVersionFeatures(29) } returns mockVersionFeaturesAndroid10

        observeViewModel(viewModel)
        viewModel.performConcurrentNetworkRequests()

        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(
                    listOf(
                        mockVersionFeaturesOreo,
                        mockVersionFeaturesPie,
                        mockVersionFeaturesAndroid10
                    )
                ),
            ),
            receivedStates
        )
    }

    private fun observeViewModel(viewModel: PerformConcurrentNetworkRequestsViewModel) {
        viewModel.uiState.observeForever { uiState ->
            receivedStates.add(uiState)
        }
    }
}