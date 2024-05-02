package com.rm.android_fundamentals.topics.t1_uicomponents.viewpagers

object RvTestData {
    fun getStringData(): List<String> {
        val dataList = arrayListOf<String>()
        repeat(50) {
            dataList.add("Item: $it")
        }
        return dataList
    }
}