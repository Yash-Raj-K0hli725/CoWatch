package com.cranoxz.streamroom.data.remote.repository

import com.cranoxz.streamroom.core.network.Konnect
import com.cranoxz.streamroom.data.remote.model.request.Lobby.Create
import com.cranoxz.streamroom.data.remote.model.response.CreateRoom
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartyRepositoryImpl @Inject constructor() {
    suspend fun createRoom(name: String) =
        Konnect<CreateRoom> {
            method = HttpMethod.Post
            url("api/room/create")
            contentType(ContentType.Application.Json)
            setBody(Create(name))
        }

}