package com.rm.android_fundamentals.topics.t9_coroutinesflow

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityCoroutinesBinding
import com.rm.android_fundamentals.topics.t9_coroutinesflow.base.CoroutineUseCaseActivity
import com.rm.android_fundamentals.topics.t9_coroutinesflow.base.UseCaseCategory
import com.rm.android_fundamentals.topics.t9_coroutinesflow.base.UseCaseCategoryAdapter
import com.rm.android_fundamentals.topics.t9_coroutinesflow.base.useCaseCategories

class CoroutinesActivity : BaseActivity() {

    private lateinit var binding: ActivityCoroutinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rv.apply {
            adapter = UseCaseCategoryAdapter(useCaseCategories, onUseCaseCategoryClickListener)
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@CoroutinesActivity)
            //addItemDecoration(initItemDecorator(this@CoroutinesActivity))
        }
    }

    private val onUseCaseCategoryClickListener: (UseCaseCategory) -> Unit = { clickedUseCaseCategory ->
        val intent = CoroutineUseCaseActivity.newIntent(applicationContext, clickedUseCaseCategory)
        startActivity(intent)
    }


    override fun getTitleToolbar() = "Coroutines Activity"
}