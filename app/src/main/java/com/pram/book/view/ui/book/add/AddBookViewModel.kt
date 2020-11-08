package com.pram.book.view.ui.book.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pram.book.MainApplication
import com.pram.book.data.model.BookModel
import com.pram.book.data.repository.BookRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddBookViewModel : ViewModel() {

    @Inject
    lateinit var bookRepository: BookRepository

    private val _isLoading = MutableLiveData<Boolean>()

    private val _isAddNewBookDone = MutableLiveData<Boolean?>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val isAddNewBookDone: LiveData<Boolean?>
        get() = _isAddNewBookDone

    init {
        MainApplication.appComponent.inject(this)
        _isAddNewBookDone.value = null
    }

    fun addNewBook(title: String, author: String, pages: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val book = BookModel(title, author, pages)
            _isAddNewBookDone.value = bookRepository.addNewBook(book)
            _isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}