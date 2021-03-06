package com.pram.book.view.ui.book.update

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

class UpdateBookViewModel() : ViewModel() {

    @Inject
    lateinit var bookRepository: BookRepository

    private val _book = MutableLiveData<BookModel>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _isUpdateBookDone = MutableLiveData<Boolean?>()

    val book: LiveData<BookModel>
        get() = _book

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val isUpdateBookDone: LiveData<Boolean?>
        get() = _isUpdateBookDone

    init {
        MainApplication.appComponent.inject(this)
        _isUpdateBookDone.value = null
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

    fun updateBook(newTitle: String, newAuthor: String, newPages: String) {
        _isLoading.value = true
        _book.value?.apply {
            title = newTitle
            author = newAuthor
            pages = newPages
        }
        viewModelScope.launch {
            _book.value?.let {
                _isUpdateBookDone.value = bookRepository.updateBook(it)
                _isLoading.value = false
            }
        }
    }
}