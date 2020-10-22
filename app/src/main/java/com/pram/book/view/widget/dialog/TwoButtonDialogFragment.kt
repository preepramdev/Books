package com.pram.book.view.widget.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.pram.book.databinding.DialogTwoButtonBinding

class TwoButtonDialogFragment : DialogFragment() {
    private lateinit var binding: DialogTwoButtonBinding
    private var message: String? = null
    private var positive: String? = null
    private var negative: String? = null

    interface OnDialogListener {
        fun onTwoButtonPositiveClick()
        fun onTwoButtonNegativeClick()
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
        binding = DialogTwoButtonBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvMessage.text = message
            btnSubmit.apply {
                text = positive
                setOnClickListener {
                    val listener = onDialogListener
                    listener?.onTwoButtonPositiveClick()
                    dismiss()
                }
            }
            btnNegative.apply {
                text = negative
                setOnClickListener {
                    val listener = onDialogListener
                    listener?.onTwoButtonNegativeClick()
                    dismiss()
                }
            }
        }
    }

    private val onDialogListener: OnDialogListener?
        private get() {
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
        outState.putString(KEY_POSITIVE, positive)
        outState.putString(KEY_NEGATIVE, negative)
    }

    private fun restoreInstanceState(bundle: Bundle) {
        message = bundle.getString(KEY_MESSAGE)
        positive = bundle.getString(KEY_POSITIVE)
        negative = bundle.getString(KEY_NEGATIVE)
    }

    private fun restoreArguments(bundle: Bundle?) {
        message = bundle?.getString(KEY_MESSAGE)
        positive = bundle?.getString(KEY_POSITIVE)
        negative = bundle?.getString(KEY_NEGATIVE)
    }

    class Builder {
        private var message: String? = null
        private var positive: String? = null
        private var negative: String? = null
        fun setMessage(message: String?): Builder {
            this.message = message
            return this
        }

        fun setPositive(positive: String?): Builder {
            this.positive = positive
            return this
        }

        fun setNegative(negative: String?): Builder {
            this.negative = negative
            return this
        }

        fun build(): TwoButtonDialogFragment {
            return newInstance(message, positive, negative)
        }
    }

    companion object {
        private const val KEY_MESSAGE = "key_message"
        private const val KEY_POSITIVE = "key_positive"
        private const val KEY_NEGATIVE = "key_negative"
        fun newInstance(
            message: String?,
            positive: String?,
            negative: String?
        ): TwoButtonDialogFragment {
            val fragment = TwoButtonDialogFragment()
            val bundle = Bundle()
            bundle.putString(KEY_MESSAGE, message)
            bundle.putString(KEY_POSITIVE, positive)
            bundle.putString(KEY_NEGATIVE, negative)
            fragment.arguments = bundle
            return fragment
        }
    }
}