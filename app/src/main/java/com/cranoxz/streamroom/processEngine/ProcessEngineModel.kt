package com.cranoxz.streamroom.processEngine

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cranoxz.streamroom.BuildConfig
import com.cranoxz.streamroom.core.domain.IDLE
import com.cranoxz.streamroom.core.domain.Loadin
import com.cranoxz.streamroom.core.domain.Message
import com.cranoxz.streamroom.core.domain.Response
import com.cranoxz.streamroom.core.domain.UI
import com.cranoxz.streamroom.data.remote.model.Succezz
import com.cranoxz.streamroom.data.remote.model.XError
import com.cranoxz.streamroom.data.remote.model.response.CreateRoom
import com.cranoxz.streamroom.data.remote.repository.PartyRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProcessEngineModel @Inject constructor(val repo: PartyRepositoryImpl) : ViewModel() {

    private val _state = MutableStateFlow<UI<out CreateRoom>>(IDLE)
    val state get() = _state.asSharedFlow()

    fun createParty(partyname: String, filesize: Long, uri: Uri) {
        viewModelScope.launch {
            _state.emit(Loadin)
            val response = when (val response = repo.createRoom(partyname)) {
                is XError -> Message(response.message, response.thrown)
                is Succezz -> {
                    startUpload(
                        roomID = response.data.room_id,
                        url = response.data.upload_url,
                        filesize = filesize,
                        uri = uri
                    )
                    Response(response.data)
                }

                else -> IDLE
            }
            if (BuildConfig.DEBUG)
                Log.d(TAG, "$response")
            _state.tryEmit(response)
        }
    }

    private fun startUpload(roomID: String, url: String, filesize: Long, uri: Uri) {
        viewModelScope.launch {
            repo.upload(url = url, filesize = filesize, uri = uri).collectLatest { response ->
                if (response is Succezz) {
                    repo.onUploadComplete(roomID)
                }
            }
        }
    }
}

private const val TAG = "Process-Engine"