package com.himorfosis.kelolabelanja.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.himorfosis.kelolabelanja.R
import kotlinx.android.synthetic.main.dialog_bottom_status.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class DisconnectBottomDialog(context: Context) : BottomSheetDialog(context) {

    init {

        setContentView(R.layout.dialog_bottom_status)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        close_dialog_img.onClick {
            dismiss()
        }
        action_dialog_btn.onClick {
            dismiss()
        }

        title_dialog_message_tv.text = context.getString(R.string.disconnect)
        description_dialog_message_tv.text = context.getString(R.string.disconnect_message)

    }
}