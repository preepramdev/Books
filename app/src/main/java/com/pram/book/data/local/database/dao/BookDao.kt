package com.pram.book.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pram.book.data.model.BookModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBooks(bookModels: List<BookModel>)

    @Query("SELECT * FROM books")
    fun getBooks() : Flow<List<BookModel>>

    @Query("SELECT * FROM books WHERE bookId = :bookId")
    fun getBook(bookId: String) : Flow<BookModel?>

    @Query("DELETE FROM books")
    suspend fun removeBooks()
}