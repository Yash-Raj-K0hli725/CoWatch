package com.cranoxz.streamroom.processEngine

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
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProcessEngineModel @Inject constructor(val repo: PartyRepositoryImpl) : ViewModel() {

    private val _state =
        MutableSharedFlow<UI>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val state get() = _state.asSharedFlow()

    fun createParty(partyname: String, filesize: Long, uri: Uri) {
        viewModelScope.launch {
            val x = when (val response = repo.createRoom(partyname)) {
                is Loading -> Loadin
                is XError -> Message(response.message, response.thrown)
                is Succezz -> {
                    startUpload(response.data.upload_url, filesize, uri)
                    Response(response.data)
                }

                else -> IDLE
            }
            Log.d("yash", "$x")
            _state.tryEmit(x)
        }
    }

    private fun startUpload(url: String, filesize: Long, uri: Uri) {
        viewModelScope.launch {
            repo.upload(url = url, filesize = filesize, uri = uri)
        }
    }

}