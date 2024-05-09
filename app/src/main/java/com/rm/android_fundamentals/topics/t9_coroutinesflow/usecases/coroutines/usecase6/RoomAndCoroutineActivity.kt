package com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.coroutines.usecase6

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import com.rm.android_fundamentals.AndroidFundamentalsApp
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityRoomAndCoroutineBinding
import com.rm.android_fundamentals.topics.t9_coroutinesflow.base.useCase6Description
import com.rm.android_fundamentals.utils.fromHtml
import com.rm.android_fundamentals.utils.setGone
import com.rm.android_fundamentals.utils.setVisible
import com.rm.android_fundamentals.utils.toast

class RoomAndCoroutineActivity : BaseActivity() {

    private val binding by lazy {
        ActivityRoomAndCoroutineBinding.inflate(layoutInflater)
    }

    private val viewModel: RoomAndCoroutineViewModel by viewModels {
        ViewModelFactory((application as AndroidFundamentalsApp).roomAndCoroutineRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.uiState.observe(this) { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        }

        binding.btnLoadVersion.setOnClickListener {
            viewModel.loadData()
        }

        binding.btnClearDb.setOnClickListener {
            viewModel.clearDatabase()
        }
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> onLoad(uiState)
            is UiState.Success -> onSuccess(uiState)
            is UiState.Error -> onError(uiState)
        }
    }

    private fun onLoad(loadingUiState: UiState.Loading) = with(binding) {
        when(loadingUiState) {
            UiState.Loading.LoadFromDb -> {
                progressBarLoadFromDb.setVisible()
                textViewLoadFromDatabase.setVisible()
                imageViewDatabaseLoadSuccessOrError.setGone()
            }

            UiState.Loading.LoadFromNetwork -> {
                progressBarLoadFromNetwork.setVisible()
                textViewLoadFromNetwork.setVisible()
                imageViewNetworkLoadSuccessOrError.setGone()
            }
        }
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        when (uiState.dataSource) {
            DataSource.DATABASE -> {
                progressBarLoadFromDb.setGone()
                imageViewDatabaseLoadSuccessOrError.setImageDrawable(
                    AppCompatResources.getDrawable(this@RoomAndCoroutineActivity, R.drawable.ic_check_green_24dp))
                imageViewDatabaseLoadSuccessOrError.setVisible()
            }

            DataSource.NETWORK -> {
                progressBarLoadFromNetwork.setGone()
                imageViewNetworkLoadSuccessOrError.setImageDrawable(
                    AppCompatResources.getDrawable(this@RoomAndCoroutineActivity, R.drawable.ic_check_green_24dp))
                imageViewNetworkLoadSuccessOrError.setVisible()
            }
        }

        val readableVersions = uiState.recentVersions.map { "API ${it.apiLevel}: ${it.name}" }
        tvResult.text = fromHtml(
            "<b>Recent Android Versions [from ${uiState.dataSource.name}]</b><br>${readableVersions.joinToString(
                separator = "<br>"
            )}"
        )
    }

    private fun onError(uiState: UiState.Error) = with(binding) {
        when (uiState.dataSource) {
            DataSource.DATABASE -> {
                progressBarLoadFromDb.setGone()
                imageViewDatabaseLoadSuccessOrError.setImageDrawable(
                    AppCompatResources.getDrawable(this@RoomAndCoroutineActivity, R.drawable.ic_clear_red_24dp))
                imageViewDatabaseLoadSuccessOrError.setVisible()
            }

            DataSource.NETWORK -> {
                progressBarLoadFromNetwork.setGone()
                imageViewNetworkLoadSuccessOrError.setImageDrawable(
                    AppCompatResources.getDrawable(this@RoomAndCoroutineActivity, R.drawable.ic_clear_red_24dp))
                imageViewDatabaseLoadSuccessOrError.setVisible()
            }
        }
        toast(uiState.message)
    }

    override fun getTitleToolbar() = useCase6Description
}
