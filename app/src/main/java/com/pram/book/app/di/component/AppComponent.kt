package com.pram.book.app.di.component

import com.pram.book.app.di.module.ApiModule
import com.pram.book.app.di.module.AppModule
import com.pram.book.app.di.module.DatabaseModule
import com.pram.book.data.api.ApiClient
import com.pram.book.data.repository.BookRepository
import dagger.Component

@Component(modules = [AppModule::class, ApiModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(client: ApiClient)

    fun inject(repository: BookRepository)
}