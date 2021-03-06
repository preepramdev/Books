package com.pram.book.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pram.book.data.local.database.dao.BookDao
import com.pram.book.data.model.BookModel

@Database(entities = [BookModel::class], version = 1)
abstract class MainDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
}