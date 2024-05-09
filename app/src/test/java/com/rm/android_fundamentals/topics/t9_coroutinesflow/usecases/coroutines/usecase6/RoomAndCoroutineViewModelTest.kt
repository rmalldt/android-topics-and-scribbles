package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase6

import com.rm.android_fundamentals.extensions.InstantExecutorExtension
import com.rm.android_fundamentals.extensions.MainDispatcherExtension
import com.rm.android_fundamentals.utils.FakeDatabase
import com.rm.android_fundamentals.utils.FakeSuccessApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class, MainDispatcherExtension::class)
class RoomAndCoroutineViewModelTest {

    private val fakeDatabase = FakeDatabase()
    private val fakeSuccessApi = FakeSuccessApi()
    private lateinit var viewModel: RoomAndCoroutineViewModel


    @BeforeEach
    fun setUp() {
    }

    @Test
    fun loadData() = runTest {

        // Repository uses runTest's TestScope
        val repository = RoomAndCoroutineRepository(this, fakeDatabase, fakeSuccessApi)


        // Other TestScope to test if the repository completes even after the
        val testScope = TestScope(SupervisorJob())
        testScope.launch {
            repository.fetchAndStoreAndroidVersionData()
            fail("Scope should be cancelled before versions are loaded")
        }
        testScope.advanceUntilIdle()
        testScope.cancel()

        assertEquals(true, fakeDatabase.insertedIntoDb)
    }

    @Test
    fun clearDatabase() {
    }
}