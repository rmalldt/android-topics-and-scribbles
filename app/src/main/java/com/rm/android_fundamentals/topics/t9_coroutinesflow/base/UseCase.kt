package com.rm.android_fundamentals.topics.t9_coroutinesflow.base

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase1.PerformSingleNetworkRequestActivity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase2.SequentialNetworkRequestsActivity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase2.callbacks.SequentialNetworkRequestsCallbacksActivity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase2.rx.SequentialNetworkRequestsRxActivity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase3.PerformConcurrentNetworkRequestsActivity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase4.TimeoutAndRetryActivity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase5.TimeoutWithRetriesActivity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase6.RoomAndCoroutineActivity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase7.CalculationInBackgroundActivity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.flow.usecase1.FlowUseCase1Activity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.flow.usecase2.FlowUseCase2Activity
import kotlinx.parcelize.Parcelize

@Parcelize
data class UseCase(
    val description: String,
    val targetActivity: Class<out AppCompatActivity>
) : Parcelable

@Parcelize
data class UseCaseCategory(val categoryName: String, val useCases: List<UseCase>) : Parcelable

const val useCase1Description = "#1 Perform single network request"
const val useCase2Description = "#2 Perform two sequential network requests"
const val useCase2UsingCallbacksDescription = "#2 Using Callbacks"
const val useCase2UsingRxDescription = "#2 Using RxJava"
const val useCase3Description = "#3 Perform several network requests concurrently"
const val useCase4Description = "#4 Network request with TimeOut and Retry"
const val useCase5Description = "#5 Timeout with Retry Network request"
const val useCase5UsingCallbacksDescription = "#5 Using callbacks"
const val useCase5UsingRxDescription = "#5 Using RxJava"
const val useCase6Description = "#6 Room and Coroutines"
const val useCase7Description = "#7 Offload expensive calculation to background thread"
const val useCase8Description = "#8 Debugging Coroutines"
const val useCase11Description = "#11 Cooperative Cancellation"
const val useCase12Description = "#12 Offload expensive calculation to several coroutines"
const val useCase13Description = "#13 Exception Handling"
const val useCase14Description = "#14 Continue Coroutine when User leaves screen"
const val useCase15Description = "#15 Using WorkManager with Coroutines"
const val useCase16Description = "#16 Performance Analysis of dispatchers, number of coroutines and yielding"
const val useCase17Description = "#17 Perform heavy calculation on Main Thread without freezing the UI"

private val coroutinesUseCases =
    UseCaseCategory(
        "Coroutine Use Cases",
        listOf(
            UseCase(useCase1Description, PerformSingleNetworkRequestActivity::class.java),
            UseCase(useCase2Description, SequentialNetworkRequestsActivity::class.java),
            UseCase(useCase2UsingCallbacksDescription, SequentialNetworkRequestsCallbacksActivity::class.java),
            UseCase(useCase2UsingRxDescription, SequentialNetworkRequestsRxActivity::class.java),
            UseCase(useCase3Description, PerformConcurrentNetworkRequestsActivity::class.java),
            UseCase(useCase4Description, TimeoutAndRetryActivity::class.java),
            UseCase(useCase5Description, TimeoutWithRetriesActivity::class.java),
            UseCase(useCase6Description, RoomAndCoroutineActivity::class.java),
            UseCase(useCase7Description, CalculationInBackgroundActivity::class.java),
        )
    )

const val flowUseCase1Description = "#1 Flow Basics"
const val flowUseCase2Description = "#2 Basic Intermediate operators"
const val flowUseCase3Description = "#3 Exception Handling"
const val flowUseCase4Description = "#4 Exposing Flows in the ViewModel"

private val flowUseCases =
    UseCaseCategory(
        "Flow Use Cases",
        listOf(
            UseCase(flowUseCase1Description, FlowUseCase1Activity::class.java),
            UseCase(flowUseCase4Description, FlowUseCase2Activity::class.java)
        )
    )

val useCaseCategories = listOf(
    coroutinesUseCases,
    flowUseCases
)