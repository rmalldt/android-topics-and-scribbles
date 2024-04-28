package com.rm.android_fundamentals.topics.t6_viewlayouts.s1_recyclerview

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityRecyclerViewBinding

class RecyclerViewExActivity : BaseActivity() {

    lateinit var binding: ActivityRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addView()
    }

    @SuppressLint("ResourceType")
    private fun addView() {
        val set = ConstraintSet()
        val tv = TextView(this)
        tv.id = 101
        tv.text = "RecyclerView Activity"
        tv.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        tv.textSize = 28F
        binding.root.addView(tv)
        set.connect(tv.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
        set.connect(tv.id, ConstraintSet.TOP, binding.includeToolbar.root.id, ConstraintSet.BOTTOM, 0)
        set.connect(tv.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
        set.constrainHeight(tv.id, 200)
        set.applyTo(binding.root)
    }

    override fun getTitleToolbar() = "RecyclerView Activity"
}
