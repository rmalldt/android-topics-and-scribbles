package com.rm.android_fundamentals.topics.t5_intents

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityRedirectionIntentsBinding
import com.rm.android_fundamentals.utils.toast
import java.util.Calendar

class RedirectionIntentsActivity : BaseActivity() {
    private lateinit var binding: ActivityRedirectionIntentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRedirectionIntentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMap.setOnClickListener { openMap() }

        binding.btnCall.setOnClickListener { callNumber() }

        binding.btnWebpage.setOnClickListener { openWebPage() }

        binding.btnCalendar.setOnClickListener { openCalendar() }

        binding.btnEmailAttach.setOnClickListener { sendEmailAttachment() }
    }

    private fun openMap() {
        val location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California")
        val mapIntent = Intent(Intent.ACTION_VIEW, location)

        try {
            startActivity(mapIntent)
        } catch (e: ActivityNotFoundException) {
            this@RedirectionIntentsActivity.toast("No apps found to open the map")
        }
    }

    private fun callNumber() {
        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:55512345"))
        try {
            startActivity(callIntent)
        } catch (e: ActivityNotFoundException) {
            this@RedirectionIntentsActivity.toast("No apps found to call the number")
        }
    }

    private fun openWebPage() {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.android.com"))
        try {
            startActivity(webIntent)
        } catch (e: ActivityNotFoundException) {
            this@RedirectionIntentsActivity.toast("No apps found to open the webpage")
        }
    }


    private fun openCalendar() {
        val calendarIntent = Intent(Intent.ACTION_INSERT, Events.CONTENT_URI).apply {
            val beginTime: Calendar = Calendar.getInstance().apply {
                set(2024, 0, 23, 7, 30)
            }
            val endTime = Calendar.getInstance().apply {
                set(2024, 0, 23, 10, 30)
            }
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
            putExtra(Events.TITLE, "Ninja Class")
            putExtra(Events.EVENT_LOCATION, "Kobra Kai")
        }
        try {
            startActivity(calendarIntent)
        } catch (e: ActivityNotFoundException) {
            this@RedirectionIntentsActivity.toast("No apps found to open the calendar")
        }
    }

    private fun sendEmailAttachment() {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("jim@in.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Email subject")
            putExtra(Intent.EXTRA_TEXT, "Email message text")
            putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/email/attachment"))
        }
        try {
            startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {
            this@RedirectionIntentsActivity.toast("No apps found to attach email")
        }
    }

    override fun getTitleToolbar() = "Redirection Intent Activity"
}
