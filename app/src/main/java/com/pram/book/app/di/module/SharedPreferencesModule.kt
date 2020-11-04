package com.pram.book.app.di.module

import android.content.Context
import android.content.SharedPreferences
import com.pram.book.data.local.sharedpreferences.MainSharedPreferences
import dagger.Module
import dagger.Provides

@Module
class SharedPreferencesModule {

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("pram_book_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideMainSharedPreferences(): MainSharedPreferences {
        return MainSharedPreferences()
    }
}