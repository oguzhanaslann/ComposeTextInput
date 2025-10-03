package com.oguzhanaslann.compose.ui

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.content.TransferableContent
import androidx.compose.foundation.content.consume
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RichContentViewModel : ViewModel() {
    var selectedImages by mutableStateOf<List<Uri>>(emptyList())

    @OptIn(ExperimentalFoundationApi::class)
    fun handleContent(
        transferableContent: TransferableContent
    ): TransferableContent? {
        val newUris = mutableListOf<Uri>()
        val remaining = transferableContent.consume { it ->
            newUris += it.uri ?: return@consume false
            true
        }
        selectedImages = newUris
        return remaining
    }
}