package com.pram.book.data.repository

import com.pram.book.data.api.ApiClient
import com.pram.book.data.api.service.BookApiService
import com.pram.book.data.database.MainDatabaseManager
import com.pram.book.data.database.dao.BookDao
import com.pram.book.data.model.BookModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BookRepository private constructor() {
    private val bookApiService: BookApiService = ApiClient.getInstance().getBookApiService()
    private val bookDao: BookDao = MainDatabaseManager.getMainDatabase().bookDao()

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
        GlobalScope.launch(Dispatchers.IO) {
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
        GlobalScope.launch(Dispatchers.IO) {
            bookDao.removeBooks()
            bookDao.updateBooks(books)
        }
    }

    companion object {
        private var instance: BookRepository? = null

        @Synchronized
        fun getInstance(): BookRepository {
            if (instance == null) {
                instance = BookRepository()
            }
            return instance as BookRepository
        }
    }
}