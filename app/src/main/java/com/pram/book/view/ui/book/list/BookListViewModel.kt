package com.pram.book.view.ui.book.list

import androidx.lifecycle.*
import com.pram.book.MainApplication
import com.pram.book.data.model.BookModel
import com.pram.book.data.repository.BookRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListViewModel : ViewModel() {
    @Inject
    lateinit var bookRepository: BookRepository

    private val _books = MutableLiveData<List<BookModel>>()

    val books : LiveData<List<BookModel>>
        get() = _books

    init {
        MainApplication.appComponent.inject(this)
    }

    fun getBooks() {
        viewModelScope.launch {
            bookRepository.getBooks().collect { books ->
                books.let {
                    _books.value = books
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}