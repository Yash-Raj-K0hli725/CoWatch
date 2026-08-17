package com.cranoxz.streamroom.core.domain

sealed class UI<K>

object Loadin : UI<Nothing>()
object IDLE : UI<Nothing>()
object Empty : UI<Nothing>()
data class Message<T>(val text: String, val extra: T? = null) : UI<Nothing>()
data class Response<K>(val data: K):UI<K>()