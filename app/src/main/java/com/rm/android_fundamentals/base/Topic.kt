package com.rm.android_fundamentals.base

import androidx.appcompat.app.AppCompatActivity
import com.rm.android_fundamentals.topics.t1_recyclerview.RecyclerViewExActivity
import com.rm.android_fundamentals.topics.t2_savedinstancestate.SavedInstanceStateCounterActivity
import com.rm.android_fundamentals.topics.t3_passdatabetweenactivities.ResultActivity

data class Topic(
    val description: String,
    val targetActivity: Class<out AppCompatActivity>
)

const val topic1Description = "1. Recycler view"
const val topic2Description = "2. Saved instance state"
const val topic3Description = "3. Start activity for result"


val topicsList = listOf(
    Topic(topic1Description, RecyclerViewExActivity::class.java),
    Topic(topic2Description, SavedInstanceStateCounterActivity::class.java),
    Topic(topic3Description, ResultActivity::class.java)
)