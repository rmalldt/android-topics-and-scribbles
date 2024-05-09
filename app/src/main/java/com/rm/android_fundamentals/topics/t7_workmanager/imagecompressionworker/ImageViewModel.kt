package com.rm.android_fundamentals.topics.t7_workmanager.imagecompressionworker

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ImageViewModel  : ViewModel() {

    var _uncompressedUri = MutableStateFlow(null)


}