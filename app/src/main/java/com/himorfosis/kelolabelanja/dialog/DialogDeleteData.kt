package com.himorfosis.kelolabelanja.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.himorfosis.kelolabelanja.R
import kotlinx.android.synthetic.main.alert_layout_delete_data.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class DialogDeleteData(var message: String) : DialogFragment() {

    lateinit var onClickItem: DialogDeleteCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.alert_layout_delete_data, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        message_dialog_tv.text = message

        action_btn.onClick {
            onClickItem.onAcceptClicked()
        }

        cancel_btn.onClick {
            dismiss()
        }

        close_img.onClick {
            dismiss()
        }

    }

    interface DialogDeleteCallback {
        fun onAcceptClicked()
    }

    fun setOnclick(onClickItem: DialogDeleteCallback) {
        this.onClickItem = onClickItem

    }

}