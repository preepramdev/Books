package com.pram.book.view.ui.book.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pram.book.R
import com.pram.book.data.model.BookModel
import com.pram.book.databinding.FragmentBookListBinding
import com.pram.book.view.widget.recycleview.adapter.BookItemAdapter
import kotlinx.android.synthetic.main.fragment_book_list.*

class BookListFragment : Fragment() {

    private lateinit var binding: FragmentBookListBinding
    private lateinit var viewModel: BookListViewModel
    private val bookItemAdapter = BookItemAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(BookListViewModel::class.java)

        bookItemAdapter.setOnItemClickListener(object : BookItemAdapter.OnItemClickListener {
            override fun onBookItemClick(book: BookModel) {
                this@BookListFragment.findNavController().navigate(
                    BookListFragmentDirections.actionToBookDetailFragment(book.bookId)
                )
            }
        })

        binding.apply {
            rcvBooks.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = bookItemAdapter
            }

            fabAddBook.apply {
                setOnClickListener {
                    this@BookListFragment.findNavController().navigate(
                        BookListFragmentDirections.actionToAddBookFragment()
                    )
                }
            }
        }

        observerViewModel()
    }

    private fun observerViewModel() {
        viewModel.books.observe(viewLifecycleOwner, { books ->
            books?.let {
                bookItemAdapter.updateItemModels(books)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBooks()
    }
}