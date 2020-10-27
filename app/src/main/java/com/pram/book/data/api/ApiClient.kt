package com.pram.book.data.api

import com.pram.book.MainApplication
import com.pram.book.data.api.service.BookApiService
import javax.inject.Inject

class ApiClient() {

    @Inject
    lateinit var bookApiService: BookApiService

    init {
        MainApplication.appComponent.inject(this)
    }
}