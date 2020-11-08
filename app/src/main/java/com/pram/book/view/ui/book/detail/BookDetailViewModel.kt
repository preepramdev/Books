package com.pram.book.view.ui.book.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pram.book.MainApplication
import com.pram.book.data.model.BookModel
import com.pram.book.data.repository.BookRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookDetailViewModel() : ViewModel() {

    @Inject
    lateinit var bookRepository: BookRepository

    private val _book = MutableLiveData<BookModel>()

    private val _isLoading = MutableLiveData<Boolean>()

    private val _isRemoveBookDone = MutableLiveData<Boolean>()

    val book : LiveData<BookModel>
        get() = _book

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val isRemoveBookDone: LiveData<Boolean>
        get() = _isRemoveBookDone

    init {
        MainApplication.appComponent.inject(this)
    }

    fun getBook(bookId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            bookRepository.getBook(bookId).collect { book ->
                book.let {
                    _book.value = book
                    _isLoading.value = false
                }
            }
        }
    }

    fun refreshBook(bookId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            bookRepository.getBook(bookId, remoteOnly = true).collect { book ->
                book.let {
                    _book.value = book
                    _isLoading.value = false
                }
            }
        }
    }

    fun removeBook(bookId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            _isRemoveBookDone.value = bookRepository.removeBook(bookId)
            _isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}