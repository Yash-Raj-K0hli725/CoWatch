package com.cranoxz.streamroom.data.remote.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.cranoxz.streamroom.BuildConfig
import com.cranoxz.streamroom.core.domain.OnProgress
import com.cranoxz.streamroom.core.network.Konnect
import com.cranoxz.streamroom.data.remote.model.Empty
import com.cranoxz.streamroom.data.remote.model.Loading
import com.cranoxz.streamroom.data.remote.model.Succezz
import com.cranoxz.streamroom.data.remote.model.XD
import com.cranoxz.streamroom.data.remote.model.request.Lobby.Create
import com.cranoxz.streamroom.data.remote.model.response.CreateRoom
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.content.ChannelWriterContent
import io.ktor.http.contentType
import io.ktor.utils.io.jvm.javaio.toOutputStream
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartyRepositoryImpl @Inject constructor(@param:ApplicationContext private val context: Context) {
    suspend fun createRoom(name: String) =
        Konnect<CreateRoom> {
            method = HttpMethod.Post
            url("api/room/create")
            contentType(ContentType.Application.Json)
            setBody(Create(name))
        }

    private val ktor = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = 60 * 60 * 1000L // 1 hour for slow uploads
            connectTimeoutMillis = 30_000L
            socketTimeoutMillis = 60 * 60 * 1000L
        }
    }

    fun upload(url: String, filesize: Long, uri: Uri) = flow {
        emit(Loading)
        val stream = context.contentResolver.openInputStream(uri) ?: return@flow
        val response = ktor.put(url) {
            header(HttpHeaders.ContentType, "video/mp4")
            setBody(
                ChannelWriterContent(
                    body = {
                        stream.use { input ->
                            input.copyTo(this.toOutputStream())
                        }
                    },
                    contentType = ContentType.Video.MP4,
                    contentLength = filesize
                )
            )
            onUpload { sent, contentLength ->
                val total =
                    if (contentLength == -1L || contentLength == null) filesize else contentLength
                OnProgress(sent, total)
            }
        }
        emit(Succezz(data = Empty))
        if(BuildConfig.DEBUG)
        Log.d(TAG, "video-upload:: ${response.bodyAsText()}")
    }

    suspend fun onUploadComplete(roomID:String){
        val response = Konnect<ResponseBody> {
            method = HttpMethod.Put
            url(urlString = "api/room/upload/complete/$roomID")
        }
        if(BuildConfig.DEBUG)
            Log.d(TAG,"on upload complete - $response")
    }
}

private const val TAG = "Part-Repo"