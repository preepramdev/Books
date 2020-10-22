package com.pram.book.view.ui.book.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pram.book.data.model.BookModel
import com.pram.book.data.repository.BookRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UpdateBookViewModel() : ViewModel() {
    private val bookRepository: BookRepository = BookRepository.getInstance()

    private val _book = MutableLiveData<BookModel>()
    private val _isUpdateBookDone = MutableLiveData<Boolean?>()

    val book: LiveData<BookModel>
        get() = _book

    val isUpdateBookDone: LiveData<Boolean?>
        get() = _isUpdateBookDone

    init {
        _isUpdateBookDone.value = null
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

    fun updateBook(newTitle: String, newAuthor: String, newPages: String) {
        _book.value?.apply {
            title = newTitle
            author = newAuthor
            pages = newPages
        }
        viewModelScope.launch {
            _book.value?.let {
                _isUpdateBookDone.value = bookRepository.updateBook(it)
            }
        }
    }
}