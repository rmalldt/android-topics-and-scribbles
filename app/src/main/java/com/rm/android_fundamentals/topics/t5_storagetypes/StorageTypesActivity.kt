package com.rm.android_fundamentals.topics.t5_storagetypes

import android.Manifest
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.rm.android_fundamentals.base.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityStorageTypesBinding
import com.rm.android_fundamentals.utils.hasPermission
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class StorageTypesActivity : BaseActivity() {

    private lateinit var binding: ActivityStorageTypesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageTypesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissions()

        selectStorageType()
    }

    private fun selectStorageType() {
        binding.apply {
            btnCreateFile.setOnClickListener {
                when (rg.checkedRadioButtonId) {
                    rbInternal.id -> internalStorageWriteFile()
                    rbInternalCache.id -> internalStorageWriteCacheFile()
                    else -> {}
                }
            }

            btnReadFile.setOnClickListener {
                when (rg.checkedRadioButtonId) {
                    rbInternal.id -> internalStorageReadFile()
                    rbInternalCache.id -> internalStorageReadCacheFile()
                    else -> {}
                }
            }
        }
    }

    private fun internalStorageWriteFile() {
        val dir = File(filesDir, INTERNAL_STORAGE_TEST_DIR)
        if (!dir.exists()) {
            dir.mkdir()
        }

        val file = File(dir, "testFile1.txt")
        val fileContent = "Some content for testing, line 1.\nTesting line 2.\nTesting line 3."
        binding.tvFileName.text = file.absolutePath

        writeFileContent(file, fileContent)
    }

    private fun internalStorageReadFile() {
        val dir = File(filesDir, INTERNAL_STORAGE_TEST_DIR)
        val file = File(dir, "testFile1.txt")
        val result = readFileContent(file)
        binding.tvFileContent.text = result
    }

    private lateinit var tempFileName: String  // temp files are appended with random numbers
    private fun internalStorageWriteCacheFile() {
        val file = File.createTempFile("testCacheFile1", ".txt", cacheDir)
        binding.tvFileName.text = file.absolutePath
        tempFileName = file.absolutePath.substringAfterLast('/')
        val fileContent = "Content for cache file"
        writeFileContent(file, fileContent)
    }

    private fun internalStorageReadCacheFile() {
        val file = File(cacheDir, tempFileName)
        val result = readFileContent(file)
        binding.tvFileContent.text = result
    }

    private fun checkPermissions() {
        val requiredPermissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (!hasPermission(this, *requiredPermissions)) {
            ActivityCompat.requestPermissions(this,
                requiredPermissions, READ_WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun readFileContent(file: File): String {
        val result = StringBuilder()
        if (file.exists()) {
            BufferedReader(FileReader(file)).use {
                while (true) {
                    val line = it.readLine() ?: break
                    result.append(line)
                    result.append('\n')
                }
            }
        }
        return result.toString()
    }

    private fun writeFileContent(file: File, fileContent: String) {
        BufferedWriter(FileWriter(file)).use {
            it.append(fileContent).flush()
        }
    }

    override fun getTitleToolbar(): String = "Storage types activity"

    companion object {
        private const val READ_WRITE_EXTERNAL_STORAGE = 1001
        private const val INTERNAL_STORAGE_TEST_DIR = "test_dir"
    }
}