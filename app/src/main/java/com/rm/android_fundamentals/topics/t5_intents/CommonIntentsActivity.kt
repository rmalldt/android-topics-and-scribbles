package com.rm.android_fundamentals.topics.t5_intents

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.AlarmClock
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityCommonIntentsBinding
import com.rm.android_fundamentals.utils.hasPermission
import com.rm.android_fundamentals.utils.toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CommonIntentsActivity : BaseActivity() {

    private lateinit var binding: ActivityCommonIntentsBinding
    private lateinit var photoUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommonIntentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissions()

        setAlarm()

        binding.btnStartCamera.setOnClickListener {
            dispatchTakePicture()
        }
    }

    private fun setAlarm() {
        binding.run {
            btnAlarm.setOnClickListener {
                var message = editTxtMsg.text.toString()
                if (message.isEmpty()) message = DEFAULT_ALARM_MSG

                val hours = editTxtHrs.text.toString()
                val minutes = editTxtMins.text.toString()
                if (hours.isNotEmpty() && minutes.isNotEmpty()) {
                    createAlarm(message, hours.toInt(), minutes.toInt())
                } else {
                    this@CommonIntentsActivity.toast("Please input time.")
                }
            }
        }
    }

    private fun createAlarm(message: String, hour: Int, minutes: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minutes)
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    /**
     * Start camera activity for result with the camera launcher.
     */
    private fun dispatchTakePicture() {
        // Use FileProvider to get the image uri
        photoUri = FileProvider.getUriForFile(
            this,
            "com.rm.android_fundamentals.FileProvider",
            createImageFile())
        cameraLauncher.launch(photoUri)
    }

    /**
     * Create image file in the external app-specific files directory.
     */
    private fun createImageFile(): File {
        val timeStamp: String? =
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HH:mm:ss"))
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}", ".jpg", storageDir)
    }

    /**
     * Create a camera activity launcher to take picture and save it on the passed uri.
     */
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { imgSavedToUri ->
        if (imgSavedToUri) {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, photoUri))
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, photoUri)
            }
            binding.imgViewCaptured.setImageBitmap(bitmap)
            saveImgToGallery(bitmap)
        } else {
            this@CommonIntentsActivity.toast("Uri result null")
        }
    }

    /**
     * Save the image to external public storage's picture directory (DIRECTORY_DCIM).
     */
    private fun saveImgToGallery(bitmap: Bitmap) {
        val timeStamp: String? = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HH:mm:ss"))
        val outputStream: OutputStream?
        /**
         * MediaStore no longer index the files stored in external app-specific directory.
         * such as getExternalFilesDir() where the current uri is being stored.
         * So, MediaScannerConnection.scanFile() returns null.
         *
         * So, manually inserting image values to get the uri for writing out
         * the image bitmap to save the image in the gallery.
         */
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        }

        val resolver = contentResolver

        val uri: Uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            ?: throw IOException("Failed to create a new MediaStore record")
        outputStream = resolver.openOutputStream(uri)

        outputStream?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, it)
        }
    }

    private val activityResultLauncherForMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // Handle Permission granted/rejected
            permissions.entries.forEach {
                val isGranted = it.value
                if (isGranted) {
                    toast("All permission granted!")
                } else {
                    toast("Permissions denied!")
                }
            }
        }


    private fun checkPermissions() {
        activityResultLauncherForMultiplePermissions.launch(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO)
            } else {
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        )
    }

    override fun getTitleToolbar(): String = "Common Intents Activity"

    companion object {
        const val DEFAULT_ALARM_MSG = "My Alarm"
        private const val PERMISSION_CODE = 1001
    }
}
