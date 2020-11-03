package com.pram.book.data.repository

import com.pram.book.MainApplication
import com.pram.book.data.api.service.BookApiService
import com.pram.book.data.database.dao.BookDao
import com.pram.book.data.model.BookModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookRepository() {
    @Inject
    lateinit var bookApiService: BookApiService
    @Inject
    lateinit var bookDao: BookDao

    init {
        MainApplication.appComponent.inject(this)
    }

    fun getBooks(): Flow<List<BookModel>> {
        fetchBooksFromRemote()
        return fetchBooksFromDatabase()
    }

    fun getBook(bookId: String): Flow<BookModel?> {
        return bookDao.getBook(bookId)
    }

    suspend fun addNewBook(book: BookModel): Boolean {
        val response = bookApiService.addNewBook(book)
        return response.isSuccessful
    }

    suspend fun updateBook(book: BookModel): Boolean {
        val response = bookApiService.updateBook(book.bookId, book)
        fetchBooksFromRemote()
        return response.isSuccessful
    }

    suspend fun removeBook(bookId: String): Boolean {
        val response = bookApiService.removeBook(bookId)
        return response.isSuccessful
    }

    private fun fetchBooksFromRemote() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = bookApiService.getBooks()
            if (response.isSuccessful) {
                val books = response.body()
                books?.let {
                    storeBookLocally(books)
                }
            }
        }
    }

    private fun fetchBooksFromDatabase(): Flow<List<BookModel>> {
        return bookDao.getBooks()
    }

    private fun storeBookLocally(books: List<BookModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            bookDao.removeBooks()
            bookDao.updateBooks(books)
        }
    }
}