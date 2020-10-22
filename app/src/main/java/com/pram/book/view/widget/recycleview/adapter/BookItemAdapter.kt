package com.pram.book.view.widget.recycleview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pram.book.R
import com.pram.book.data.model.BookModel
import com.pram.book.view.widget.recycleview.viewholder.BookItemViewHolder

class BookItemAdapter(private val itemModels: ArrayList<BookModel>) : RecyclerView.Adapter<BookItemViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onBookItemClick(book: BookModel)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    fun updateItemModels(newItemModels: List<BookModel>) {
        itemModels.clear()
        itemModels.addAll(newItemModels)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val itemModel = itemModels[position]

        holder.apply {
            setBookId(itemModel.bookId)
            setBookTitle(itemModel.title)
            setBookAuthor(itemModel.author)
            setBookPage(itemModel.pages)
            itemView.setOnClickListener {
                mOnItemClickListener.let {
                    if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                        mOnItemClickListener.onBookItemClick(itemModel)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemModels.size
    }
}