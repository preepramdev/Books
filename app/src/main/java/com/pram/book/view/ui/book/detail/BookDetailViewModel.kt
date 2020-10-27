package com.pram.book.view.ui.book.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pram.book.MainApplication
import com.pram.book.data.model.BookModel
import com.pram.book.data.repository.BookRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookDetailViewModel() : ViewModel() {
    @Inject
    lateinit var bookRepository: BookRepository

    private val _book = MutableLiveData<BookModel>()

    private val _isRemoveBookDone = MutableLiveData<Boolean>()

    val book : LiveData<BookModel>
        get() = _book

    val isRemoveBookDone: LiveData<Boolean>
        get() = _isRemoveBookDone

    init {
        MainApplication.appComponent.inject(this)
    }

    fun getBook(bookId: String) {
        viewModelScope.launch {
            bookRepository.getBook(bookId).collect { book ->
                book.let {
                    _book.value = book
                }
            }
        }
    }

    fun removeBook(bookId: String) {
        viewModelScope.launch {
            _isRemoveBookDone.value = bookRepository.removeBook(bookId)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}