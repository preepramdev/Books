package com.pram.book

import android.app.Application
import com.pram.book.app.di.component.AppComponent
import com.pram.book.app.di.component.DaggerAppComponent
import com.pram.book.app.di.module.AppModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}