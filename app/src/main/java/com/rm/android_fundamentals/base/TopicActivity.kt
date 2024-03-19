package com.rm.android_fundamentals.base

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.ActivityTopicBinding
import com.rm.android_fundamentals.utils.initItemDecorator


class TopicActivity : BaseActivity() {

    private val topic by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_TOPIC, Topic::class.java)!!
        } else {
            intent.getParcelableExtra(EXTRA_TOPIC)!!
        }
    }

    private lateinit var binding: ActivityTopicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter = SubTopicAdapter(topic, onTopicClickedListener())
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@TopicActivity)
            addItemDecoration(initItemDecorator(this@TopicActivity))
        }
    }

    private fun onTopicClickedListener(): (SubTopic) -> Unit = { clickedSubTopic ->
        val intent = Intent(this, clickedSubTopic.targetActivity)
        startActivity(intent)
    }

    override fun getTitleToolbar() = "Topic Activity"

    companion object {
        private const val EXTRA_TOPIC = "EXTRA_TOPIC"

        fun newIntent(context: Context, topic: Topic) =
            Intent(context, TopicActivity::class.java).apply {
                putExtra(EXTRA_TOPIC, topic)
            }
    }
}