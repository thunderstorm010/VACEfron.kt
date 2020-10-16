@file:Suppress("MemberVisibilityCanBePrivate")

package me.thunderstorm.commons.api

import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.ByteArrayInputStream
import kotlin.properties.Delegates

abstract class ApiUtil {
    protected var client = OkHttpClient()
    protected abstract var baseUrl: String

    protected abstract fun parseErr(body: ResponseBody)

    protected open fun makeWebRequest(endpoint: String, arguments: MutableMap<String, String>): JsonObject {
        val builder = Request.Builder()
            .url("$baseUrl/$endpoint${parseArgs(arguments)}")
        var responseString = ""
        client.newCall(builder.build()).execute().use { response ->
            response.body!!.use { body ->
                if (response.code != 200) {
                    parseErr(body)
                }
                responseString = body.string()
            }
        }
        return Json.parse(responseString).asObject()
    }


    protected fun parseArgs(arguments: MutableMap<String, String>): String {
        when {
            arguments.isEmpty() -> return ""
            arguments.size == 1 -> {
                val first = arguments.entries.first()
                return "?${first.key}=${first.value}"
            }
            arguments.size > 1 -> {
                val first = arguments.entries.first()
                var base = "?${first.key}=${first.value}"
                arguments.remove(first.key)
                for (argument in arguments) {
                    base += "&${argument.key}=${argument.value}"
                }
                return base
            }
        }
        // Dummy code
        throw Exception("")
    }

    protected open fun makeImageRequest(endpoint: String, arguments: MutableMap<String, String>): ByteArrayInputStream {
        val builder = Request.Builder()
            .url("$baseUrl/$endpoint${parseArgs(arguments)}")
        var responseArray by Delegates.notNull<ByteArrayInputStream>()
        client.newCall(builder.build()).execute().use { response ->
            response.body!!.use { body ->
                if (response.code != 200) {
                    parseErr(body)
                }
                responseArray = ByteArrayInputStream(body.bytes())
            }
        }
        return responseArray
    }
}