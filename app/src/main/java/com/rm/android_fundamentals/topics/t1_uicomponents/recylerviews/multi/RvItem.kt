package com.rm.android_fundamentals.topics.t1_uicomponents.recylerviews.multi

fun forLinearViewHolders(): List<LinearItem>  {
    val list = arrayListOf<LinearItem>()
    repeat(5) {
        list.add(LinearItem("Linear Item: $it", RvItemType.Linear))
    }
    return list
}

fun forMultipleViewHolders(): List<RvItem> {
    val list = arrayListOf<RvItem>()
    repeat(4) {
        list.add(LinearItem("Linear Item: $it", RvItemType.Linear))
    }
    repeat(5) {
        list.add(GridItem("Grid item: $it", RvItemType.Grid))
    }
    repeat(3) {
        list.add(LinearItem("Linear Item: $it", RvItemType.Linear))
    }
    return list
}

data class LinearItem(
    val text: String,
    override val itemType: RvItemType
) : RvItem {
    override fun getItemType(): Int {
        return RvItemType.Linear.ordinal
    }
}

data class GridItem(
    val text: String,
    override val itemType: RvItemType
) : RvItem {
    override fun getItemType(): Int {
        return RvItemType.Grid.ordinal
    }
}

interface RvItem {
    val itemType: RvItemType
    fun getItemType(): Int
}

enum class RvItemType(value: Int) {
    Linear(0),
    Grid(1)
}


