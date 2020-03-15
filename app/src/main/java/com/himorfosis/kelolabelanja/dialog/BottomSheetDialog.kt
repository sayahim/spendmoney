package com.himorfosis.kelolabelanja.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.himorfosis.kelolabelanja.R
import kotlinx.android.synthetic.main.dialog_bottom_message.*
import kotlinx.android.synthetic.main.input_finance.*
import kotlinx.android.synthetic.main.input_finance.close_img


class BottomSheetDialog {

    class InputFinance(context: Context) : BottomSheetDialog(context) {

        init {
            setContentView(R.layout.input_finance)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            close_img.setOnClickListener {

                dismiss()
            }

            save_btn.setOnClickListener {

            }

        }

    }

    class Message(context: Context, getTitle: String, getMessage: String) : BottomSheetDialog(context) {


        init {
            setContentView(R.layout.dialog_bottom_message)

            title_message_tv.text = getTitle
            description_message_tv.text = getMessage

            close_message_img.setOnClickListener {
                dismiss()
            }

            action_btn.setOnClickListener {
                dismiss()
            }

        }

    }

}