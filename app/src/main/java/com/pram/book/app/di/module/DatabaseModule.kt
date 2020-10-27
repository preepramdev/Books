package com.pram.book.app.di.module

import android.content.Context
import androidx.room.Room
import com.pram.book.data.database.MainDatabase
import com.pram.book.data.database.dao.BookDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideMainDatabase(context: Context): MainDatabase {
        return Room.databaseBuilder(
            context,
            MainDatabase::class.java,
            "pram_book.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideBookDao(mainDatabase: MainDatabase): BookDao {
        return mainDatabase.bookDao()
    }
}