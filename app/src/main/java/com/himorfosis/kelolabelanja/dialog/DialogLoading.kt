package com.himorfosis.kelolabelanja.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.github.mikephil.charting.utils.Utils.init
import com.himorfosis.kelolabelanja.R
import org.jetbrains.anko.layoutInflater

class DialogLoading(context: Context):Dialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_loading)
        this.setCancelable(false)
    }

}