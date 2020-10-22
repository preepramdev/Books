package com.pram.book.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pram.book.data.api.ApiClient
import com.pram.book.data.database.dao.BookDao
import com.pram.book.data.model.BookModel

@Database(entities = [BookModel::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}