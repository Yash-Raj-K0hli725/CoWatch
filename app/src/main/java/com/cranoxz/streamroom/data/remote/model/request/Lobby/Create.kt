package com.cranoxz.streamroom.data.remote.model.request.Lobby

import com.google.gson.annotations.SerializedName

data class Create(@SerializedName("room_name") val name: String)
