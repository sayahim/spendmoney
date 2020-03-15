package com.himorfosis.kelolabelanja.dialog

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.himorfosis.kelolabelanja.R
import kotlinx.android.synthetic.main.dialog_bottom_message.*



class BottomSheetDialog {

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