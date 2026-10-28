package com.cranoxz.streamroom.root

import android.app.Application
import com.cranoxz.loxer.domain.initFirebase
import com.cranoxz.loxer.wrapper.timber
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidApp
class RootApplication : Application(), Thread.UncaughtExceptionHandler {
    @Inject
    lateinit var tree: Timber.Tree

    override fun uncaughtException(t: Thread, e: Throwable) {
        timber.e("[exception-handler]", e)
    }

    override fun onCreate() {
        super.onCreate()
        initFirebase()
        Timber.plant(tree)
    }
}