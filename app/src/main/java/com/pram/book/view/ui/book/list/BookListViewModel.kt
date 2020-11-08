package com.pram.book.view.ui.book.list

import androidx.lifecycle.*
import com.pram.book.MainApplication
import com.pram.book.data.model.BookModel
import com.pram.book.data.repository.BookRepository
import com.pram.book.data.local.sharedpreferences.MainSharedPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListViewModel : ViewModel() {

    @Inject
    lateinit var bookRepository: BookRepository

    @Inject
    lateinit var preferences: MainSharedPreferences

    private val _books = MutableLiveData<List<BookModel>>()

    private val _isLoading = MutableLiveData<Boolean>()

    val books: LiveData<List<BookModel>>
        get() = _books

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        MainApplication.appComponent.inject(this)
    }

    fun getBooks() {
        _isLoading.value = true
        if (preferences.isFirstLaunch) {
            refreshBooks()
        } else {
            viewModelScope.launch {
                bookRepository.getBooks().collect { books ->
                    books.let {
                        _books.value = books
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun refreshBooks() {
        _isLoading.value = true
        viewModelScope.launch {
            bookRepository.getBooks(remoteOnly = true)
                .collect { books ->
                    books.let {
                        _books.value = books
                        _isLoading.value = false
                        preferences.isFirstLaunch = false
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}