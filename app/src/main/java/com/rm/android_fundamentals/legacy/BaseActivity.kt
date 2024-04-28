package com.rm.android_fundamentals.legacy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rm.android_fundamentals.R

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getTitleToolbar(): String

    override fun onStart() {
        super.onStart()

        setToolbarTitle(getTitleToolbar())
        getUpButton().setOnClickListener {
            finish()
        }
    }

    private fun getUpButton(): ImageView = findViewById(R.id.btnToolbarBack)
    private fun getToolbarTitleLabel(): TextView = findViewById(R.id.toolbarTitle)

    fun hideButton() {
        getUpButton().visibility = View.GONE
    }

    private fun setToolbarTitle(toolbarTitle: String) {
        getToolbarTitleLabel().text = toolbarTitle
    }
}
