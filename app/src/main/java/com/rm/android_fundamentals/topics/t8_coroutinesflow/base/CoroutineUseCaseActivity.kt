package com.rm.android_fundamentals.topics.t8_coroutinesflow.base

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityCoroutineUseCaseBinding

class CoroutineUseCaseActivity : BaseActivity() {

    private val useCaseCategory by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_USE_CASE_CATEGORY, UseCaseCategory::class.java)!!
        } else {
            intent.getParcelableExtra(EXTRA_USE_CASE_CATEGORY)!!
        }
    }

    lateinit var binding: ActivityCoroutineUseCaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineUseCaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rv.apply {
            adapter = UseCaseAdapter(useCaseCategory, onUseCaseClickListener)
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@CoroutineUseCaseActivity)
            //addItemDecoration(initItemDecorator(this@CoroutineUseCaseActivity))
        }
    }

    private val onUseCaseClickListener: (UseCase) -> Unit = { clickedUseCase ->
        startActivity(Intent(this@CoroutineUseCaseActivity, clickedUseCase.targetActivity))
    }

    override fun getTitleToolbar() = useCaseCategory.categoryName

    companion object {
        private const val EXTRA_USE_CASE_CATEGORY = "EXTRA_USE_CASES"

        fun newIntent(context: Context, useCaseCategory: UseCaseCategory) =
            Intent(context, CoroutineUseCaseActivity::class.java).apply {
                putExtra(EXTRA_USE_CASE_CATEGORY, useCaseCategory)
            }
    }
}