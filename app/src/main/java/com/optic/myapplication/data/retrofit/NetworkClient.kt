package com.optic.myapplication.data.retrofit

import android.content.Context
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

    private var retrofit: Retrofit? = null
    private var scarlet: Scarlet? = null

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()

        retrofit = Retrofit.Builder().apply {
            baseUrl("https://chatup-backend-i6fa.onrender.com/api/")
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()


        scarlet = Scarlet.Builder().apply {
            webSocketFactory(client.newWebSocketFactory("wss://chatup-backend-i6fa.onrender.com/api/ws"))
            addMessageAdapterFactory(GsonMessageAdapter.Factory())
            addStreamAdapterFactory(RxJava2StreamAdapterFactory())
        }.build()
    }

    @JvmStatic
    fun getRetrofit(context: Context?): Retrofit? {
        return retrofit
    }

    @JvmStatic
    fun getScarlet(): Scarlet? {
        return scarlet
    }
}
