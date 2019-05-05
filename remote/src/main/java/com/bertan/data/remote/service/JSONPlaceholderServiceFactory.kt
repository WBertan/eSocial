package com.bertan.data.remote.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object JSONPlaceholderServiceFactory {
    fun build(isDebug: Boolean): JSONPlaceholderService =
        createLoggingInterceptor(isDebug)
            .let(::createOkHttpClient)
            .let(::createJSONPlaceholderService)

    private fun createJSONPlaceholderService(okHttpClient: OkHttpClient): JSONPlaceholderService =
        Retrofit.Builder()
            .baseUrl(JSONPlaceholderService.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(JSONPlaceholderService::class.java)

    private fun createOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

    private fun createLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (isDebug) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
        }
}