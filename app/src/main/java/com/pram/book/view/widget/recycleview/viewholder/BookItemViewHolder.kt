package com.pram.book.view.widget.recycleview.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_book.view.*

class BookItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setBookId(bookId: String) {
        itemView.tvBookId.text = bookId
    }

    fun setBookTitle(bookTitle: String) {
        itemView.tvBookTitle.text = bookTitle
    }

    fun setBookAuthor(bookAuthor: String) {
        itemView.tvBookAuthor.text = bookAuthor
    }

    fun setBookPage(bookPage: String) {
        itemView.tvBookPages.text = bookPage
    }
}