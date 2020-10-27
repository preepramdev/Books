package com.pram.book.view.widget.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.pram.book.databinding.DialogOneButtonBinding

class OneButtonDialogFragment : DialogFragment() {
    private lateinit var binding: DialogOneButtonBinding
    private var message: String? = null
    private var submit: String? = null

    interface OnDialogListener {
        fun onOneButtonClick()
    }

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
                    val listener = onDialogListener
                    listener?.onOneButtonClick()
                    dismiss()
                }
            }
        }
    }

    private val onDialogListener: OnDialogListener?
        get() {
            val fragment = parentFragment
            try {
                return if (fragment != null) {
                    fragment as OnDialogListener?
                } else {
                    activity as OnDialogListener?
                }
            } catch (ignored: ClassCastException) {
            }
            return null
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

        fun build(): OneButtonDialogFragment {
            return newInstance(message, submit)
        }
    }

    companion object {
        private const val KEY_MESSAGE = "key_message"
        private const val KEY_SUBMIT = "key_submit"
        fun newInstance(message: String?, submit: String?): OneButtonDialogFragment {
            val fragment = OneButtonDialogFragment()
            val bundle = Bundle()
            bundle.putString(KEY_MESSAGE, message)
            bundle.putString(KEY_SUBMIT, submit)
            fragment.arguments = bundle
            return fragment
        }
    }
}