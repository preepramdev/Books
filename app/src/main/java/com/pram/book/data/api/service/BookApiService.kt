package com.pram.book.data.api.service

import com.pram.book.data.model.BookModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface BookApiService {
    @GET("books")
    suspend fun getBooks(): Response<List<BookModel>>

    @POST("books")
    suspend fun addNewBook(@Body book: BookModel): Response<BookModel?>

    @PUT("books/{id}")
    suspend fun updateBook(@Path("id") bookId: String, @Body book: BookModel): Response<BookModel?>

    @DELETE("books/{id}")
    suspend fun removeBook(@Path("id") bookId: String): Response<Void>
}