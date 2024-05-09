package com.rm.android_fundamentals.topics.t7_workmanager.imagecompressionworker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.roundToInt

class ImageCompressionWorker(
    private val context: Context,
    private val params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val uriString = params.inputData.getString(KEY_INPUT_URI)
        val compressionThresholdInBytes = params.inputData.getLong(KEY_COMPRESSION_THRESHOLD, 0L)

        val uri = Uri.parse(uriString)
        val bytes = context.contentResolver.openInputStream(uri)?.use {
            it.readBytes()
        } ?: return@withContext Result.failure()

        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

        var outputBytes: ByteArray
        var quality = 100

        do {
            val outputStream = ByteArrayOutputStream()
            outputStream.use { byteStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteStream)
                outputBytes = outputStream.toByteArray()
                quality -= (quality * 0.1).roundToInt()
            }
        } while(outputBytes.size > compressionThresholdInBytes && quality > 5)

        val file = File(context.cacheDir, "${params.id}.jpeg")
        file.writeBytes(outputBytes)

        // Returning the file path not the file object, there is a size constraint
        // hence using ByteArrayOutputStream
        Result.success(workDataOf(KEY_RESULT_PATH to file.absoluteFile))
    }

    companion object {
        const val KEY_INPUT_URI = "KEY_INPUT_URI"
        const val KEY_COMPRESSION_THRESHOLD = "KEY_COMPRESSION_THRESHOLD"
        const val KEY_RESULT_PATH = "KEY_RESULT_PATH"
    }
}