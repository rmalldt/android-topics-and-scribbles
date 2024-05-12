package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rm.android_fundamentals.mocknetwork.mock.MockApi
import com.rm.android_fundamentals.utils.MainDispatcherRule
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

class PerformSingleNetworkRequestViewModelTest {

    private val mockApi: MockApi = mockk()
    private lateinit var performSingleNetworkRequestViewModel: PerformSingleNetworkRequestViewModel

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule: TestRule = MainDispatcherRule()

    @Before
    fun setUp() {
        performSingleNetworkRequestViewModel = PerformSingleNetworkRequestViewModel(mockApi)
    }


    /*@Test
    fun `performSingleNetwork should return Success when network request is successful`() = runTest {
        // GIVEN
        coEvery { mockApi.getRecentAndroidVersions() } returns mockAndroidVersions

        // WHEN
        performSingleNetworkRequestViewModel.performSingleNetworkRequest()


        // THEN
        performSingleNetworkRequestViewModel.uiState.value?.let { assertTrue(it == UiState.Success(
            mockAndroidVersions)) }
    }*/
}