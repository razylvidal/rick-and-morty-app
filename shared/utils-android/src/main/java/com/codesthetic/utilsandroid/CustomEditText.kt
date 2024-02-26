package com.codesthetic.utilsandroid

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import java.util.Objects


class CustomEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {
    private var onKeyPreImeListener: OnKeyPreImeListener? = null
    private var onSuperEditTextListener: OnSuperEditTextListener? = null
    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KEYCODE_BACK) {
            if (onKeyPreImeListener != null) onKeyPreImeListener!!.onBackpressed()
            if (onSuperEditTextListener != null) onSuperEditTextListener!!.onBack()
        }
        return false
    }

    fun setOnKeyPreImeListener(onKeyPreImeListener: OnKeyPreImeListener?) {
        this.onKeyPreImeListener = onKeyPreImeListener
    }

    interface OnKeyPreImeListener {
        fun onBackpressed()
    }

    fun setOnSuperEditTextListener(onSuperEditTextListener: OnSuperEditTextListener) {
        this.onSuperEditTextListener = onSuperEditTextListener
        setOnEditorActionListener { _: TextView?, i: Int, _: KeyEvent? ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
                onSuperEditTextListener.onDone()
            }
            true
        }
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // do nothing
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // do nothing
            }
            override fun afterTextChanged(editable: Editable) {
                onSuperEditTextListener.onTextChanged(Objects.requireNonNull(text).toString())
            }
        })
    }

    fun clear() {
        onKeyPreImeListener = null
        onSuperEditTextListener = null
    }

    interface OnSuperEditTextListener {
        fun onBack()
        fun onTextChanged(text: String?)
        fun onDone()
    }
}

