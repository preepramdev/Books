package com.pram.book.view.ui.book.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pram.book.data.model.BookModel
import com.pram.book.data.repository.BookRepository
import kotlinx.coroutines.launch

class AddBookViewModel : ViewModel() {
    private val bookRepository: BookRepository = BookRepository.getInstance()

    private val _isAddNewBookDone = MutableLiveData<Boolean?>()

    val isAddNewBookDone: LiveData<Boolean?>
        get() = _isAddNewBookDone

    init {
        _isAddNewBookDone.value = null
    }

    fun addNewBook(title: String, author: String, pages: String) {
        viewModelScope.launch {
            val book = BookModel(title, author, pages)
            _isAddNewBookDone.value = bookRepository.addNewBook(book)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}