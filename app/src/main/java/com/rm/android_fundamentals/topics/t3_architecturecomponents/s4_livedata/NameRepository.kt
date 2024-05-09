package com.rm.android_fundamentals.topics.t3_architecturecomponents.s4_livedata

import kotlin.random.Random

class NameRepository {

    private val listOfNames = listOf(
        "Jim",
        "Kim",
        "Kale",
        "Tim",
        "Nolan"
    )

    fun getName(): String = listOfNames[Random.nextInt(0, 5 )]
}