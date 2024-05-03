package com.rm.android_fundamentals.topics.t1_uicomponents.recylerviews.simple

data class SimpleRvItem(
    val id: String,
    var switchState: Boolean
) {
    companion object {
        fun getSimpleRvItems(): List<SimpleRvItem> {
            val dataList = arrayListOf<SimpleRvItem>()
            repeat(50) {
                val item = SimpleRvItem("Item: $it", false)
                dataList.add(item)
            }
            return dataList
        }
    }
}


