package com.salomohutapea.fcmtutorial

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {
    @Headers("Content-Type: application/json")
    @POST("/")
    fun sendMessage(@Body data: NotificationData): Call<String>
}

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://fcm-example-1-inqj2g6cpq-et.a.run.app/") // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}

object RestApiService {
    fun sendMessage(data: NotificationData, onResult: (String?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(Api::class.java)
        retrofit.sendMessage(data).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    val resBody = response.body()
                    onResult(resBody)
                }
            }
        )
    }
}