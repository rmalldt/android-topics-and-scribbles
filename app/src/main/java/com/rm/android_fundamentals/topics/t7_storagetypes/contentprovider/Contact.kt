package com.rm.android_fundamentals.topics.t7_storagetypes.contentprovider

import android.net.Uri

data class Contact(
    val name: String,
    val number: String
)

data class Image(
    val id: Long,
    val name: String,
    val uri: Uri
)
