package com.rm.android_fundamentals.base.model

data class NavDrawerTopic(
    val title: String,
    val sections: List<NavDest>,
    var isExpanded: Boolean = false
) : INavDrawerItem
