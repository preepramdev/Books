package com.pram.book.view.widget.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.pram.book.databinding.DialogOneButtonBinding

class DismissOneButtonDialogFragment : DialogFragment() {

    private lateinit var binding: DialogOneButtonBinding
    private var message: String? = null
    private var submit: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            restoreArguments(arguments)
        } else {
            restoreInstanceState(savedInstanceState)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogOneButtonBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvMessage.text = message
            btnSubmit.apply {
                text = submit
                setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_MESSAGE, message)
        outState.putString(KEY_SUBMIT, submit)
    }

    private fun restoreInstanceState(bundle: Bundle) {
        message = bundle.getString(KEY_MESSAGE)
        submit = bundle.getString(KEY_SUBMIT)
    }

    private fun restoreArguments(bundle: Bundle?) {
        message = bundle?.getString(KEY_MESSAGE)
        submit = bundle?.getString(KEY_SUBMIT)
    }

    class Builder {
        private var message: String? = null
        private var submit: String? = null
        fun setMessage(message: String?): Builder {
            this.message = message
            return this
        }

        fun setSubmit(submit: String?): Builder {
            this.submit = submit
            return this
        }

        fun build(): DismissOneButtonDialogFragment {
            return newInstance(message, submit)
        }
    }

    companion object {
        private const val KEY_MESSAGE = "key_message"
        private const val KEY_SUBMIT = "key_submit"
        fun newInstance(message: String?, submit: String?): DismissOneButtonDialogFragment {
            val fragment = DismissOneButtonDialogFragment()
            val bundle = Bundle()
            bundle.putString(KEY_MESSAGE, message)
            bundle.putString(KEY_SUBMIT, submit)
            fragment.arguments = bundle
            return fragment
        }
    }
}