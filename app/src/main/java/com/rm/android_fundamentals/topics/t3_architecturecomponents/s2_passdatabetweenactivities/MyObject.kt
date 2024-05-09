package com.rm.android_fundamentals.topics.t3_architecturecomponents.s2_passdatabetweenactivities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyObject(val id: Int, val name: String) : Parcelable