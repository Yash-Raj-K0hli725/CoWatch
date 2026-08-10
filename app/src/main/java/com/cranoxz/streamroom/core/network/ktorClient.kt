package com.cranoxz.streamroom.core.network

import android.util.Log
import com.cranoxz.streamroom.BuildConfig
import com.cranoxz.streamroom.data.remote.model.Succezz
import com.cranoxz.streamroom.data.remote.model.XD
import com.cranoxz.streamroom.data.remote.model.XError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.gson.gson
import org.json.JSONObject
import java.net.UnknownHostException

object KtorNetwork {
    const val TAG = "Ktor"
    val Client: HttpClient by lazy {
        HttpClient(Android) {
            engine {
                connectTimeout = 15_000
                socketTimeout = 15_000
            }
            install(ContentNegotiation) {
                gson {
                    serializeNulls()
                    setLenient()
                    setPrettyPrinting()
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        // Routes Ktor's internal logging straight into Android Logcat
                        Log.d(TAG, message)
                    }
                }
                level = if (BuildConfig.DEBUG) LogLevel.NONE else LogLevel.ALL
            }
        }
    }
}

@Suppress("FunctionName")
suspend inline fun <reified T> Konnect(
    crossinline block: HttpRequestBuilder.() -> Unit
): XD<out T> {
    return try {
        val response = KtorNetwork.Client.request {
            url(BuildConfig.BASE_URL)
            block()
        }
        if (response.status.value in 200..299) {
            Succezz(response.body<T>())
        } else {
            var code = 500
            var message = "something went wrong. please try again later"
            runCatching {
                val obzect = JSONObject(response.bodyAsText())
                code = obzect.getInt("code")
                message = obzect.getString("message")
            }
            XError(
                thrown = Exception("HTTP Error"),
                message = message,
                code = code
            )
        }
    } catch (e: UnknownHostException) {
        XError("No Internet Connection. Please check your network.", e)
    } catch (e: Exception) {
        XError(e.localizedMessage ?: "An unexpected error occurred.", e)
    }
}

