package com.cranoxz.streamroom.lobby.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.cranoxz.streamroom.toaster.model.Alert
import com.cranoxz.streamroom.toaster.model.Warning
import com.cranoxz.streamroom.toaster.model.toast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class Viewmodel : ViewModel() {
    private val _partyName = MutableStateFlow("")
    val partyName get() = _partyName.asStateFlow()

    fun onNameChange(string: String) {
        _partyName.value = string
    }

    private val _uri = MutableStateFlow<Uri?>(null)
    val uri get() = _uri.asStateFlow()

    fun setVideoUri(uri: Uri) {
        this._uri.value = uri
    }

    fun onPartyCreate(toNext: () -> Unit) = when {
        _partyName.value.isEmpty() -> toast(Warning("party name cannot be empty"))
        _uri.value == null -> toast(Alert("please select a file to upload"))
        else -> toNext()
    }

}