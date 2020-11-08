package com.pram.book.view.ui.book.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pram.book.databinding.FragmentAddBookBinding
import com.pram.book.view.widget.dialog.DismissOneButtonDialogFragment
import com.pram.book.view.widget.dialog.OneButtonDialogFragment

class AddBookFragment : Fragment(), OneButtonDialogFragment.OnDialogListener {

    private lateinit var binding: FragmentAddBookBinding
    private lateinit var viewModel: AddBookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this@AddBookFragment, object : OnBackPressedCallback(true) {
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
        binding = FragmentAddBookBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AddBookViewModel::class.java)

        binding.apply {
            swLoading.apply {
                setOnRefreshListener {
                    isRefreshing = false
                }
            }

            btnAdd.apply {
                setOnClickListener {
                    val title = binding.edtBookTitle.text.toString()
                    val author = binding.edtBookAuthor.text.toString()
                    val pages = binding.edtBookPages.text.toString()

                    viewModel.addNewBook(title, author, pages)
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
            isLoading.observe(viewLifecycleOwner, { isLoading ->
                isLoading?.let {
                    binding.swLoading.isRefreshing = isLoading
                }
            })

            isAddNewBookDone.observe(viewLifecycleOwner, { isAddNewBookDone ->
                isAddNewBookDone?.let {
                    if (isAddNewBookDone) {
                        callOneButtonDialog("Done", "Ok")
                    } else {
                        callDismissOneButtonDialog("Something wrong", "Ok")
                    }
                }
            })
        }
    }

    private fun exit() {
        this@AddBookFragment.findNavController().popBackStack()
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