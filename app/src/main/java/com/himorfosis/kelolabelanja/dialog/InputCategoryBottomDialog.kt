package com.himorfosis.kelolabelanja.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat.startActivity
import com.himorfosis.kelolabelanja.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.layout_input_category.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class InputCategoryBottomDialog(context: Context) : BottomSheetDialog(context) {


    init {

        setContentView(R.layout.layout_input_category)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        save_btn.onClick {
            val name = name_category_ed.text.toString()

            if (name.isEmpty()) {
                context.toast("Harap Masukkan Nama Kategori")
            } else {
                context.startActivity(context.intentFor<HomepageActivity>(
                        "from" to "category"
                ))

                dismiss()
            }

        }

    }
}