package com.cranoxz.streamroom.core.domain.model

data class UploadProgress(
    val percent: Int = 0,
    val sent: Long = 0L,
    val total: Long = 0L
)