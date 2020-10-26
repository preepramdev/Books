package com.pram.book.view.widget.recycleview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.pram.book.databinding.ItemBookBinding

class BookItemViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setBookId(bookId: String) {
        binding.tvBookId.text = bookId
    }

    fun setBookTitle(bookTitle: String) {
        binding.tvBookTitle.text = bookTitle
    }

    fun setBookAuthor(bookAuthor: String) {
        binding.tvBookAuthor.text = bookAuthor
    }

    fun setBookPage(bookPage: String) {
        binding.tvBookPages.text = bookPage
    }
}