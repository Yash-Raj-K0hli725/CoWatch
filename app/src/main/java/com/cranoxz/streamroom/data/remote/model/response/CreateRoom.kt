package com.cranoxz.streamroom.data.remote.model.response

data class CreateRoom(
    val room_id: String,
    val room_name: String,
    val upload_url: String,
    val created_at: String
)
