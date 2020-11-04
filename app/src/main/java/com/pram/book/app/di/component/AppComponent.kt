package com.pram.book.app.di.component

import com.pram.book.app.di.module.*
import com.pram.book.data.remote.api.ApiClient
import com.pram.book.data.repository.BookRepository
import com.pram.book.data.local.sharedpreferences.MainSharedPreferences
import com.pram.book.view.ui.book.add.AddBookViewModel
import com.pram.book.view.ui.book.detail.BookDetailViewModel
import com.pram.book.view.ui.book.list.BookListViewModel
import com.pram.book.view.ui.book.update.UpdateBookViewModel
import dagger.Component

@Component(modules = [AppModule::class, ApiModule::class, DatabaseModule::class, SharedPreferencesModule::class, BookRepositoryModule::class])
interface AppComponent {

    fun inject(client: ApiClient)

    fun inject(preferences: MainSharedPreferences)

    fun inject(repository: BookRepository)

    fun inject(viewModel: AddBookViewModel)

    fun inject(viewModel: BookDetailViewModel)

    fun inject(viewModel: BookListViewModel)

    fun inject(viewModel: UpdateBookViewModel)
}