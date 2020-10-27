package com.pram.book.view.ui.book.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pram.book.databinding.FragmentUpdateBookBinding
import com.pram.book.view.widget.dialog.DismissOneButtonDialogFragment
import com.pram.book.view.widget.dialog.OneButtonDialogFragment

class UpdateBookFragment : Fragment(), OneButtonDialogFragment.OnDialogListener {

    private lateinit var binding: FragmentUpdateBookBinding
    private lateinit var viewModel: UpdateBookViewModel
    private lateinit var bookId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this@UpdateBookFragment, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exit()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        binding = FragmentUpdateBookBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            bookId = UpdateBookFragmentArgs.fromBundle(it).bookId
        }

        viewModel = ViewModelProvider(this).get(UpdateBookViewModel::class.java)
        viewModel.getBook(bookId)

        binding.apply {
            btnUpdate.apply {
                setOnClickListener {
                    val newTitle = binding.edtBookTitle.text.toString()
                    val newAuthor = binding.edtBookAuthor.text.toString()
                    val newPages = binding.edtBookPages.text.toString()

                    viewModel.updateBook(newTitle, newAuthor, newPages)
                }
            }

            btnCancel.apply {
                setOnClickListener {
                    exit()
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
                        edtBookTitle.setText(book.title)
                        edtBookAuthor.setText(book.author)
                        edtBookPages.setText(book.pages)
                    }
                }
            })

            isUpdateBookDone.observe(viewLifecycleOwner, { isUpdateBookDone ->
                isUpdateBookDone?.let {
                    if (isUpdateBookDone) {
                        callOneButtonDialog("Done", "Ok")
                    } else {
                        callDismissOneButtonDialog("Something wrong", "Ok")
                    }
                }
            })
        }
    }

    private fun exit() {
        this@UpdateBookFragment.findNavController().popBackStack()
    }

    private fun callOneButtonDialog(message: String, submit: String) {
        val fragment = OneButtonDialogFragment.Builder()
            .setMessage(message)
            .setSubmit(submit)
            .build()
        fragment.show(childFragmentManager, "OneButtonDialogFragment")
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                exit()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}