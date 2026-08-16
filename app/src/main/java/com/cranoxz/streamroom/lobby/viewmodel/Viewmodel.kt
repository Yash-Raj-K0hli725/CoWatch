package com.cranoxz.streamroom.lobby.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cranoxz.streamroom.core.domain.IDLE
import com.cranoxz.streamroom.core.domain.Loadin
import com.cranoxz.streamroom.core.domain.Message
import com.cranoxz.streamroom.core.domain.Response
import com.cranoxz.streamroom.core.domain.UI
import com.cranoxz.streamroom.data.remote.model.Loading
import com.cranoxz.streamroom.data.remote.model.Succezz
import com.cranoxz.streamroom.data.remote.model.XError
import com.cranoxz.streamroom.data.remote.repository.PartyRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Viewmodel @Inject constructor(private val repo: PartyRepositoryImpl) : ViewModel() {
    private val _partyName = MutableStateFlow("")
    val partyName get() = _partyName.asStateFlow()

    fun onNameChange(string: String) {
        _partyName.value = string
    }

    private val _state =
        MutableSharedFlow<UI>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val state get() = _state.asSharedFlow()

    private val _uri = MutableStateFlow<Uri?>(null)
    val uri get() = _uri.asStateFlow()

    fun setVideoUri(uri:Uri){
        this._uri.value = uri
    }

    fun onPartyCreate() {
        if (_partyName.value.isEmpty()) {
            _state.tryEmit(Message<Nothing>("party name cannot be empty"))
            return
        }
        viewModelScope.launch {
            val x = when (val response = repo.createRoom(partyName.value)) {
                is Loading -> Loadin
                is XError -> Message(response.message, response.thrown)
                is Succezz -> Response(response.data)
                else -> IDLE
            }
            Log.d("yash", x.toString())
            _state.tryEmit(x)
        }
    }
}