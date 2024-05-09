package com.rm.android_fundamentals.topics.t7_workmanager

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.rm.android_fundamentals.databinding.FragmentWorkManagerBinding
import com.rm.android_fundamentals.topics.t7_workmanager.imagecompressionworker.ImageCompressionWorker

class WorkManagerFragment : Fragment() {

    private var _binding: FragmentWorkManagerBinding? = null
    private val binding get() = _binding!!

    private lateinit var workManager: WorkManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWorkManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workManager = WorkManager.getInstance(requireContext())

        onIntentReceived()

    }

    private fun onIntentReceived() {
        val intent = requireActivity().intent
        if (intent.action == Intent.ACTION_SEND) {
            val imgUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableArrayExtra(Intent.EXTRA_STREAM, Uri::class.java)
            } else {
                intent?.getParcelableArrayExtra(Intent.EXTRA_STREAM)
            } ?: return

            val request = OneTimeWorkRequestBuilder<ImageCompressionWorker>()
                .setInputData(
                    workDataOf(
                        ImageCompressionWorker.KEY_INPUT_URI to imgUri,
                        ImageCompressionWorker.KEY_COMPRESSION_THRESHOLD to 1024 * 20L
                    )
                )
                .setConstraints(
                    Constraints(
                        requiredNetworkType = NetworkType.CONNECTED,
                        requiresStorageNotLow = true
                    )
                )
                .build()
            workManager.enqueue(request)
        }
    }
}
