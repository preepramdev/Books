package com.pram.book.view.ui.book.list

import androidx.lifecycle.*
import com.pram.book.data.model.BookModel
import com.pram.book.data.repository.BookRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BookListViewModel : ViewModel() {

    private val bookRepository: BookRepository = BookRepository.getInstance()

    private val _books = MutableLiveData<List<BookModel>>()

    val books : LiveData<List<BookModel>>
        get() = _books

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