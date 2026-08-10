package com.cranoxz.streamroom.data.remote.model

sealed class XD<T>

object Loading : XD<Nothing>()
object Empty : XD<Nothing>()
data class Succezz<T>(val data: T) : XD<T>()
data class XError(val message: String, val thrown: Throwable? = null, val code: Int = -1) :
    XD<Nothing>()
