package com.cranoxz.loxer.wrapper

import timber.log.Timber

@Suppress("ClassName")
object timber {
    fun e(tag: String, message: String, t: Throwable? = null) = Timber.tag(tag).e(t, message)
    fun e(tag: String, t: Throwable) = Timber.tag(tag).e(t)
    fun d(tag: String, message: String) = Timber.tag(tag).d(message)
    fun d(message: String) = Timber.tag(tag).d(message)
    fun i(message: String) = Timber.tag(tag).i(message)
    fun v(message: String) = Timber.tag(tag).v(message)
}
private const val tag = "loxer"