package com.cranoxz.streamroom.core.navigations.routes

import android.net.Uri
import kotlinx.serialization.Serializable

@Serializable
data class ProcessEngine(
    val partyname: String,

    val uri: String
)