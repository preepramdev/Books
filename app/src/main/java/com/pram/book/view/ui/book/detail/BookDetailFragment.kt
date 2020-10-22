package com.pram.book.view.ui.book.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pram.book.databinding.FragmentBookDetailBinding
import com.pram.book.view.widget.dialog.DismissOneButtonDialogFragment
import com.pram.book.view.widget.dialog.OneButtonDialogFragment
import com.pram.book.view.widget.dialog.TwoButtonDialogFragment

class BookDetailFragment : Fragment(),
    OneButtonDialogFragment.OnDialogListener,
    TwoButtonDialogFragment.OnDialogListener {

    private lateinit var binding: FragmentBookDetailBinding
    private lateinit var viewModel: BookDetailViewModel
    private lateinit var bookId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        binding = FragmentBookDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            bookId = BookDetailFragmentArgs.fromBundle(it).bookId
        }

        viewModel = ViewModelProvider(this).get(BookDetailViewModel::class.java)
        viewModel.getBook(bookId)

        binding.apply {
            btnUpdate.apply {
                setOnClickListener {
                    this@BookDetailFragment.findNavController().navigate(
                        BookDetailFragmentDirections.actionToUpdateBookFragment(bookId)
                    )
                }
            }

            btnRemove.apply {
                setOnClickListener {
                    callTwoButtonDialog("Remove?", "Yes", "No")
                }
            }
        }

        observerViewModel()
    }

    private fun observerViewModel() {
        viewModel.apply {
            book.observe(viewLifecycleOwner, { book ->
                book?.let {
                    binding.apply {
                        tvBookId.text = book.bookId
                        tvBookTitle.text = book.title
                        tvBookAuthor.text = book.author
                        tvBookPages.text = book.pages
                    }
                }
            })

            isRemoveBookDone.observe(viewLifecycleOwner, { isRemoveBookDone ->
                isRemoveBookDone?.let {
                    if (isRemoveBookDone) {
                        callOneButtonDialog("Done", "Ok")
                    } else {
                        callDismissOneButtonDialog("Something wrong", "Ok")
                    }
                }
            })
        }
    }

    private fun callOneButtonDialog(message: String, submit: String) {
        val fragment = OneButtonDialogFragment.Builder()
            .setMessage(message)
            .setSubmit(submit)
            .build()
        fragment.show(childFragmentManager, "OneButtonDialogFragment")
    }

    private fun callTwoButtonDialog(message: String, positive: String, negative: String) {
        val fragment = TwoButtonDialogFragment.Builder()
            .setMessage(message)
            .setPositive(positive)
            .setNegative(negative)
            .build()
        fragment.show(childFragmentManager, "TwoButtonDialogFragment")
    }

    private fun callDismissOneButtonDialog(message: String, submit: String) {
        val fragment = DismissOneButtonDialogFragment.Builder()
            .setMessage(message)
            .setSubmit(submit)
            .build()
        fragment.show(childFragmentManager, "DismissOneButtonDialogFragment")
    }

    override fun onOneButtonClick() {
        exit()
    }

    override fun onTwoButtonPositiveClick() {
        viewModel.removeBook(bookId)
    }

    override fun onTwoButtonNegativeClick() {}

    private fun exit() {
        this@BookDetailFragment.findNavController().popBackStack()
    }
}