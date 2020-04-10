package com.himorfosis.kelolabelanja.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import com.himorfosis.kelolabelanja.R
import kotlinx.android.synthetic.main.dialog_message.*

class DialogInfo(context: Context?, title: String = "", message: String = "") : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_message)
        actionBtn.setOnClickListener { dismiss() }
        messageTv.text = message

        if (title == "") {
            titleTv.visibility = View.INVISIBLE
        } else {
            titleTv.text = title
        }

    }
}