package com.example.quwitest.network

import android.content.SharedPreferences
import com.example.quwitest.Constants
import com.example.quwitest.data.AuthRequest
import com.example.quwitest.data.AuthResponse
import com.example.quwitest.data.Channels
import com.example.quwitest.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body data: AuthRequest): AuthResponse

    @GET("chat-channels")
    suspend fun getChannels(): Channels

    companion object {

        @Volatile
        private var instance: ApiService? = null

        private var sharedPreferences: SharedPreferences? = null


        private fun makeRetrofit(): ApiService {
            val retrofit = createRetrofit()
            val api = retrofit.create(ApiService::class.java)
            return api
        }

        private fun createRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build()
        }

        private fun createOkHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
                .addInterceptor(AuthInterceptor(sharedPreferences!!))
            return httpClient.build()
        }

        fun getInstance(sharedPreferences: SharedPreferences): ApiService {
            this.sharedPreferences = sharedPreferences
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = makeRetrofit()
                    }
                }
            }
            return instance!!
        }
    }
}