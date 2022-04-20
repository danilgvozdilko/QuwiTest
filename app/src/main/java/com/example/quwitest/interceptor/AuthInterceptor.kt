package com.example.quwitest.interceptor

import android.content.SharedPreferences
import com.example.quwitest.Constants.AUTHORIZATION
import com.example.quwitest.Constants.BEARER
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(
    private val sharedPref: SharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequestBuilder = request.newBuilder()

        sharedPref.getString("TOKEN", "")?.let {
            newRequestBuilder.header(AUTHORIZATION, "$BEARER $it")
        }

        return try {
            chain.proceed(newRequestBuilder.build())
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }
    }
}