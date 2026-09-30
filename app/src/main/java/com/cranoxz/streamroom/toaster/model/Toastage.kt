package com.cranoxz.streamroom.toaster.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class Toastage(val bread: String) {
    object Blank : Toastage("")
}

data class Warning(val message: String) : Toastage(message)
data class Succezz(val message: String) : Toastage(message)
data class Alert(val message: String) : Toastage(message)

private val _TOAST_ = MutableStateFlow<Toastage>(Toastage.Blank)
val _toast_ get() = _TOAST_.asStateFlow()

fun toast(toastage: Toastage) {
    _TOAST_.tryEmit(toastage)
}