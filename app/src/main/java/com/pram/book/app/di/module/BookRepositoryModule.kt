package com.pram.book.app.di.module

import com.pram.book.data.repository.BookRepository
import dagger.Module
import dagger.Provides

@Module
class BookRepositoryModule {

    @Provides
    fun provideBookRepository(): BookRepository {
        return BookRepository()
    }
}