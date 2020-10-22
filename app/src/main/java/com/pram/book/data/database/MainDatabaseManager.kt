package com.pram.book.data.database

import android.content.Context
import androidx.room.Room

object MainDatabaseManager {
    private lateinit var mainDatabase: MainDatabase

    fun init(context: Context) {
        mainDatabase = Room.databaseBuilder(
            context,
            MainDatabase::class.java,
            "pram_book.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun getMainDatabase(): MainDatabase = mainDatabase
}