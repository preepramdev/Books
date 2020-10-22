package com.pram.book.data.api

import com.pram.book.data.api.service.BookApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor() {
    private var bookApiService: BookApiService

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(ApiUrl.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        bookApiService = retrofit.create(BookApiService::class.java)
    }

    fun getBookApiService(): BookApiService = bookApiService

    companion object {

        private var instance: ApiClient? = null

        @Synchronized
        fun getInstance(): ApiClient {
            if (instance == null) {
                instance = ApiClient()
            }
            return instance as ApiClient
        }
    }
}