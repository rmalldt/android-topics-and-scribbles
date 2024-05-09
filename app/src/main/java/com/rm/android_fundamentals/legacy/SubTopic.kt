package com.rm.android_fundamentals.legacy

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.rm.android_fundamentals.topics.t3_architecturecomponents.s1_savedinstancestate.SavedInstanceStateActivity
import com.rm.android_fundamentals.topics.t3_architecturecomponents.s2_passdatabetweenactivities.ResultActivity
import com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments.fragmentprogrammatic.FragmentHostManualActivity
import com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments.fragmentxmlauto.FragmentHostActivity
import com.rm.android_fundamentals.topics.t2_appnavigation.s2_viewpager.HostingActivity
import com.rm.android_fundamentals.topics.t3_architecturecomponents.s3_lifecycle.withlifecyclecomponent.LifecycleActivity
import com.rm.android_fundamentals.topics.t3_architecturecomponents.s3_lifecycle.withoutlifecyclecomponent.WithoutLifeCycleComponentActivity
import com.rm.android_fundamentals.topics.t3_architecturecomponents.s5_viewmodels.ex1.DiceRollActivity
import com.rm.android_fundamentals.topics.t3_architecturecomponents.s5_viewmodels.ex2.SavedStateHandleActivity
import com.rm.android_fundamentals.topics.t3_architecturecomponents.s4_livedata.NameActivity
import com.rm.android_fundamentals.topics.t3_architecturecomponents.s6_coroutinescopes.CoroutineScopesActivity
import com.rm.android_fundamentals.topics.t4_appcomponents.AppComponentsActivity
import com.rm.android_fundamentals.topics.t5_intents.IntentActivity
import com.rm.android_fundamentals.topics.t8_storagetypes.storagetypes.StorageTypesActivity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.CoroutinesActivity
import com.rm.android_fundamentals.topics.t10_architectures.mvc.MVCActivity
import com.rm.android_fundamentals.topics.t10_architectures.mvp.MVPActivity
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubTopic(
    val description: String,
    val targetActivity: Class<out AppCompatActivity>
) : Parcelable

@Parcelize
data class Chapter(
    val description: String,
    val subTopics: List<SubTopic>
) : Parcelable

const val appEntryPointsSubtopic1 = "1. Saved instance state"
const val appEntryPointsSubtopic2 = "2. Register activity for result"
val appEntryPoints = Chapter(
    "1. App entry points",
    listOf(
        SubTopic(appEntryPointsSubtopic1, SavedInstanceStateActivity::class.java),
        SubTopic(appEntryPointsSubtopic2, ResultActivity::class.java)
    )
)

const val appNavigationSubTopic1 = "1. Fragments programmatically"
const val appNavigationSubTopic2 = "2. Fragment XML automatic"
const val appNavigationSubTopic3 = "3. ViewPager"
val appNavigation = Chapter(
    "2. App navigation",
    listOf(
        SubTopic(appNavigationSubTopic1, FragmentHostManualActivity::class.java),
        SubTopic(appNavigationSubTopic2, FragmentHostActivity::class.java),
        SubTopic(appNavigationSubTopic3, HostingActivity::class.java)
    )
)

const val architectureComponentsSubTopic1 = "1. Listener without lifecycle-aware component"
const val architectureComponentsSubTopic2 = "2. Lifecycle-aware component"
const val architectureComponentsSubTopic3 = "3. ViewModel: DiceRoll"
const val architectureComponentsSubTopic4 = "4. ViewModel: Saved state handle"
const val architectureComponentsSubTopic5 = "5. LiveData"
const val architectureComponentsSubTopic6 = "5. CoroutineScopes"
val architectureComponents = Chapter(
    "3. Architecture components",
    listOf(
        SubTopic(architectureComponentsSubTopic1, WithoutLifeCycleComponentActivity::class.java),
        SubTopic(architectureComponentsSubTopic2, LifecycleActivity::class.java),
        SubTopic(architectureComponentsSubTopic3, DiceRollActivity::class.java),
        SubTopic(architectureComponentsSubTopic4, SavedStateHandleActivity::class.java),
        SubTopic(architectureComponentsSubTopic5, NameActivity::class.java),
        SubTopic(architectureComponentsSubTopic6, CoroutineScopesActivity::class.java)
    )
)

const val appComponentSubTopic1 = "1. App component"
val appComponents = Chapter(
    "4. App components",
    listOf(
        SubTopic(appComponentSubTopic1, AppComponentsActivity::class.java )
    )
)

const val intentSubTopic1 = "1. Intent"
val intents = Chapter(
    "5. Intents",
    listOf(
        SubTopic(intentSubTopic1, IntentActivity::class.java )
    )
)

const val viewLayoutSubTopic1 = "1. View layout"
val viewLayouts = Chapter(
    "6. View layouts",
    listOf()
)

const val storageTypesSubTopic1 = "1. Storage types"
val storageTypes = Chapter(
    "7. Storage types",
    listOf(
        SubTopic(storageTypesSubTopic1, StorageTypesActivity::class.java )
    )
)

const val coroutinesSubTopic1 = "Coroutines"
const val coroutinesSubTopic2 = "Flow"
val coroutines = Chapter(
    "8. Coroutines",
    listOf(
        SubTopic(coroutinesSubTopic1, CoroutinesActivity::class.java)
    )
)

const val mvcSubTopic1 = "MVC"
const val mvpSubTopic2 = "MVP"
val architectures = Chapter(
    "9. Architectures",
    listOf(
        SubTopic(mvcSubTopic1, MVCActivity::class.java),
        SubTopic(mvpSubTopic2, MVPActivity::class.java)
    )
)

val topics = listOf(
    appEntryPoints,
    appNavigation,
    architectureComponents,
    appComponents,
    intents,
    viewLayouts,
    storageTypes,
    coroutines,
    architectures
)