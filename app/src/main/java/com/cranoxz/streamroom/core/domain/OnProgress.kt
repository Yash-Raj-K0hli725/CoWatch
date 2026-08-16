package com.cranoxz.streamroom.core.domain

import com.cranoxz.streamroom.core.domain.model.UploadProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private val _progress = MutableStateFlow(UploadProgress(0, 0, 0))
val progress get() = _progress.asStateFlow()
@Suppress("FunctionName")
suspend fun OnProgress(sent: Long, total: Long) =
    _progress.emit(UploadProgress((sent * 100f / total).toInt(), sent, total))

