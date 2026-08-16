package com.cranoxz.streamroom.data.remote.model.response

data class CreateRoom(
    val room_id: String,
    val video_url: String,
    val room_name: String,
    val started_at: String
)
