package com.rm.android_fundamentals

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.base.Topic
import com.rm.android_fundamentals.base.TopicAdapter
import com.rm.android_fundamentals.base.topicsList
import com.rm.android_fundamentals.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideButton()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter = TopicAdapter(topicsList, onTopicClickedListener())
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(initItemDecorator())
        }
    }

    private fun onTopicClickedListener(): (Topic) -> Unit = {
        val intent = Intent(applicationContext, it.targetActivity)
        startActivity(intent)
    }


    private fun initItemDecorator(): DividerItemDecoration {
        val itemDecorator = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.recyclerview_divider)!!)
        return itemDecorator
    }

    override fun getTitleToolbar() = "Android fundamentals"
}
