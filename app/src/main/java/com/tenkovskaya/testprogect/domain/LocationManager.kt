package com.tenkovskaya.testprogect.domain

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class LocationManager {

    private val endpoint = "http://ip-api.com/json"

    fun getCountry(callback: (String) -> Unit) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(endpoint)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.e("TAGGG", "Request failed with exception: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { responseBody ->
                    val responseJson = JSONObject(responseBody)
                    val countryCode = responseJson.getString("countryCode")
                    callback(countryCode)
                    Log.d("TAGGG", "Received countryCode: $countryCode")
                }
            }
        })
    }
}


