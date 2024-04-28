package com.rm.android_fundamentals.base.model

import androidx.appcompat.app.AppCompatActivity

data class NavDrawerSection(
    val title: String,
    val targetFragmentId: Int
) : INavDrawerItem