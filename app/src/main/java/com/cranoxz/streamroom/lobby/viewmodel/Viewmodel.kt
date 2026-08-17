package com.cranoxz.streamroom.lobby.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.cranoxz.streamroom.core.domain.IDLE
import com.cranoxz.streamroom.core.domain.Message
import com.cranoxz.streamroom.core.domain.UI
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow


class Viewmodel : ViewModel() {
    private val _partyName = MutableStateFlow("")
    val partyName get() = _partyName.asStateFlow()

    fun onNameChange(string: String) {
        _partyName.value = string
    }

    private val _state =
        MutableSharedFlow<UI<Nothing>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val state get() = _state.asSharedFlow()

    private val _uri = MutableStateFlow<Uri?>(null)
    val uri get() = _uri.asStateFlow()

    fun setVideoUri(uri: Uri) {
        this._uri.value = uri
    }

    fun onPartyCreate(toNext: () -> Unit) {
        val err = when {
            _partyName.value.isEmpty() -> Message<Nothing>("party name cannot be empty")
            _uri.value == null -> Message<Nothing>("please select a file to upload")
            else -> IDLE
        }
        if (err != IDLE) {
            _state.tryEmit(err)
            return
        }
        toNext()
    }
}