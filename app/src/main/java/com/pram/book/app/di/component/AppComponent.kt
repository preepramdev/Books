package com.pram.book.app.di.component

import com.pram.book.app.di.module.ApiModule
import com.pram.book.app.di.module.AppModule
import com.pram.book.app.di.module.BookRepositoryModule
import com.pram.book.app.di.module.DatabaseModule
import com.pram.book.data.api.ApiClient
import com.pram.book.data.repository.BookRepository
import com.pram.book.view.ui.book.add.AddBookViewModel
import com.pram.book.view.ui.book.detail.BookDetailViewModel
import com.pram.book.view.ui.book.list.BookListViewModel
import com.pram.book.view.ui.book.update.UpdateBookViewModel
import dagger.Component

@Component(modules = [AppModule::class, ApiModule::class, DatabaseModule::class, BookRepositoryModule::class])
interface AppComponent {
    fun inject(client: ApiClient)

    fun inject(repository: BookRepository)

    fun inject(viewModel: AddBookViewModel)

    fun inject(viewModel: BookDetailViewModel)

    fun inject(viewModel: BookListViewModel)

    fun inject(viewModel: UpdateBookViewModel)
}