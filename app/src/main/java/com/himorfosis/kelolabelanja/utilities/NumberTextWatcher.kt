package com.himorfosis.kelolabelanja.utilities

import android.text.Editable
import android.text.TextWatcher

class NumberTextWatcher: TextWatcher {

    private var current = ""

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun afterTextChanged(s: Editable) {

        if (s.toString() != current) {
            val userInput = s.toString().replace("[^\\d]".toRegex(), "")

            if (userInput.length <= 16) {

                val sb = StringBuilder()
                for (i in 0 until userInput.length - 1) {
                    if (i % 4 == 0 && i > 0) {
                        sb.append(" ")
                    }
                    sb.append(userInput[i])
                }
                current = sb.toString()

//                s.filters = arrayOfNulls<InputFilter>(0)

            }
            s.replace(0, s.length, current, 0, current.length)
        }
    }

}