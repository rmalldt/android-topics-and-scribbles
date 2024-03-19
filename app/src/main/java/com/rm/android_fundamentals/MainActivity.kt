package com.rm.android_fundamentals

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.base.Topic
import com.rm.android_fundamentals.base.TopicActivity
import com.rm.android_fundamentals.base.TopicAdapter
import com.rm.android_fundamentals.base.topics
import com.rm.android_fundamentals.databinding.ActivityMainBinding
import com.rm.android_fundamentals.utils.initItemDecorator

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
            adapter = TopicAdapter(topics, onTopicClickedListener())
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(initItemDecorator(this@MainActivity))
        }
    }

    private fun onTopicClickedListener(): (Topic) -> Unit = { clickedTopic ->
        val intent = TopicActivity.newIntent(this@MainActivity, clickedTopic)
        startActivity(intent)
    }

    override fun getTitleToolbar() = "Android Fundamentals"
}
