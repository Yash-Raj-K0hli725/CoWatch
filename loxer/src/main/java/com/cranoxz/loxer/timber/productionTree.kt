package com.cranoxz.loxer.timber

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

fun productionTree(): Timber.Tree = object : Timber.Tree() {
    val analytics = FirebaseCrashlytics.getInstance()
    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?
    ) {
        val xag = tag ?: "loxer"
        when {
            priority == Log.DEBUG || priority == Log.VERBOSE -> return
            t != null -> analytics.recordException(t)
            priority == Log.ERROR -> analytics.recordException(Exception("[$xag] :: $message"))
            else -> analytics.log("[$xag] :: $message")
        }
    }
}
