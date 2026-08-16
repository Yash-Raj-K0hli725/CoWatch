package com.cranoxz.streamroom.core.domain

sealed class UI

object Loadin : UI()
object IDLE : UI()
object Empty : UI()
data class Message<T>(val text: String, val extra: T? = null) : UI()
data class Response<K>(val data: K):UI()