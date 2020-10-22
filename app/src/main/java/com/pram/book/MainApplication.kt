package com.pram.book

import android.app.Application
import com.pram.book.data.database.MainDatabaseManager

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MainDatabaseManager.init(applicationContext)
    }
}