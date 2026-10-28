package com.cranoxz.streamroom.root

import com.cranoxz.loxer.timber.productionTree
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
object Gardner {

    @Provides
    fun providesTree(): Timber.Tree =
//        if (BuildConfig.DEBUG) Timber.DebugTree() else productionTree()
        productionTree()

}