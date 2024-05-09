package com.rm.android_fundamentals.topics.t8_storagetypes.storagetypes

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import androidx.core.app.ActivityCompat
import com.rm.android_fundamentals.legacy.BaseActivity
import com.rm.android_fundamentals.databinding.ActivityStorageTypesBinding
import com.rm.android_fundamentals.utils.hasPermission
import com.rm.android_fundamentals.utils.toast
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

        if (isExternalStorageWritable() && isExternalStorageReadable()) {
            toast("External storage dirs available for read and write")
        }

        selectStorageType()
    }

    private fun selectStorageType() {
        binding.apply {
            btnCreateFile.setOnClickListener {
                when (rg.checkedRadioButtonId) {
                    rbInternal.id -> writeFile(filesDir, SUB_DIR)
                    rbInternalCache.id -> writeFile(cacheDir, null)
                    rbExternal.id -> writeFile(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!, SUB_DIR)
                    rbExternalCache.id -> writeFile(externalCacheDir!!, null)
                    rbExternalStorage.id -> writeFile(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOCUMENTS)
                    rbExternalStoragePublic.id -> writeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), SUB_DIR)
                    rbSharedPreferences.id -> writeToSharedPreferences()
                }
            }

            btnReadFile.setOnClickListener {
                when (rg.checkedRadioButtonId) {
                    rbInternal.id -> readFile(filesDir, SUB_DIR)
                    rbInternalCache.id -> readFile(cacheDir, null)
                    rbExternal.id -> readFile(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!, SUB_DIR)
                    rbExternalCache.id -> readFile(externalCacheDir!!, null)
                    rbExternalStorage.id -> readFile(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOCUMENTS)
                    rbExternalStoragePublic.id -> readFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), SUB_DIR)
                    rbSharedPreferences.id -> readFromSharedPreferences()
                }
            }

            btnListFiles.setOnClickListener {
                when (rg.checkedRadioButtonId) {
                    rbInternal.id -> listFiles(filesDir, SUB_DIR)
                    rbInternalCache.id -> listFiles(cacheDir, null)
                    rbExternal.id -> listFiles(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!, SUB_DIR)
                    rbExternalCache.id -> listFiles(externalCacheDir!!, null)
                    rbExternalStorage.id -> listFiles(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOCUMENTS)
                    rbExternalStoragePublic.id -> listFiles(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), SUB_DIR)
                }
            }
        }
    }

    private fun writeFile(parent: File, child: String?) {
        binding.apply {
            val fileName = txtInputFileName.text.toString()
            val fileContent = txtInputFileContent.text.toString()
            val dir: File?
            val file: File?
            if (fileName.isNotBlank() && fileContent.isNotBlank()) {
                if (child != null) {
                    dir = File(parent, child)
                    if (!dir.exists()) {
                        dir.mkdir()
                    }
                    file = File(dir, fileName)
                } else {
                    dir = parent
                    file = File.createTempFile(fileName, null, dir)
                }

                if (file != null) {
                    BufferedWriter(FileWriter(file)).use {
                        it.append(fileContent).flush()
                    }
                    binding.txtFileInfo.text = file.absolutePath
                }
            } else {
                toast("Please enter both file name and file content")
            }
        }
    }

    private fun listFiles(parent: File, child: String?) {
        val dir = if (child != null) File(parent, child) else parent
        val result = StringBuilder()
        result.append("Dir: $dir\n")
        if (dir.exists()) {
            dir.listFiles()?.forEachIndexed { index, value ->
                result.append("${index + 1}. ")
                result.append(value.absolutePath.substringAfterLast("/"))
                result.append('\n')
            }
        } else {
            result.append("Directory doesn't exist")
        }

        binding.txtFileInfo.text = result
        binding.txtFileInfo.movementMethod = ScrollingMovementMethod()
    }

    private fun readFile(parent: File, child: String?) {
        val dir = if (child != null) File(parent, child) else parent
        val fileName = binding.txtInputFileName.text.toString()
        if (fileName.isNotBlank()) {
            val result = StringBuilder()
            val file = File(dir, fileName)
            result.append("File: ${file.absolutePath}\n")
            if (file.exists()) {
                BufferedReader(FileReader(file)).use {
                    while (true) {
                        val line = it.readLine() ?: break
                        result.append(line)
                        result.append('\n')
                    }
                }
            } else {
                result.append("File doesn't exist")
            }
            binding.txtFileInfo.text = result
        } else {
            toast("Please enter file name")
        }
    }

    private fun writeToSharedPreferences() {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("saved_int", 10)
        editor.apply()
        binding.txtFileInfo.text = 10.toString()
    }

    private fun readFromSharedPreferences() {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val savedInt = sharedPreferences.getInt("saved_int", 0)
        binding.txtFileInfo.text = savedInt.toString()
    }

    private fun checkPermissions() {
        val requiredPermissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (!hasPermission(this, *requiredPermissions)) {
            ActivityCompat.requestPermissions(this,
                requiredPermissions, READ_WRITE_EXTERNAL_STORAGE
            )
        }
    }

    private fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    private fun isExternalStorageReadable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
    }

    override fun getTitleToolbar(): String = "Storage Types Activity"

    companion object {
        private const val READ_WRITE_EXTERNAL_STORAGE = 1001
        private const val SUB_DIR = "test_dir"
    }
}