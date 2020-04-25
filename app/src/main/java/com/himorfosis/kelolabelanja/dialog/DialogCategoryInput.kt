package com.himorfosis.kelolabelanja.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.repo.CategoryViewModel
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.utilities.preferences.CategoryPref
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_input_category.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class DialogCategoryInput(internal var context: Context, var imageAssets: String, var imageUrl: String) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_input_category, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        Picasso.with(context)
                .load(imageUrl)
                .error(R.drawable.ic_broken_image)
                .into(assets_category_img)

        save_btn.onClick {
            val name = name_category_ed.text.toString()

            if (name.isEmpty()) {
                toast("Harap Masukkan Nama Kategori")
            } else {

                viewModel.userCreateCategoryPush(name, imageAssets)
                viewModel.userCreateCategoryResponse.observe(viewLifecycleOwner, Observer {
                    dismiss()
                    when (it) {
                        is StateNetwork.OnSuccess -> {
                            toast("Berhasil Menambah Category")
                            startActivity(intentFor<HomepageActivity>("from" to "category"))
                        } else -> {
                            toast("Gagal Menambah Category")
                        }
                    }
                })

                startActivity(intentFor<HomepageActivity>(
                        "from" to "category"
                ))
                dismiss()
            }

            close_img.onClick {
                dismiss()
            }
}
        }

//    init {
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        setContentView(R.layout.layout_input_category)
//        this.setCancelable(false)
//
//
//        val viewModel = ViewModelProvider(ownerActivity).get(CategoryViewModel::class.java)
//
//        Picasso.with(context)
//                .load(imageUrl)
//                .error(R.drawable.ic_broken_image)
//                .into(assets_category_img)
//
//        save_btn.onClick {
//            val name = name_category_ed.text.toString()
//            val typeDataFinance = DataPreferences.category.getString(CategoryPref.TYPE)
//
//            if (name.isEmpty()) {
//                context.toast("Harap Masukkan Nama Kategori")
//            } else {
//
//                viewModel.userCreateCategoryPush(name, imageUrl)
//                viewModel.userCreateCategoryResponse.observe(co, Observer {
//                    dismiss()
//                    when (it) {
//                        is StateNetwork.OnSuccess -> {
//                            context.toast("Berhasil Menambah Category")
//                            context.startActivity(context.intentFor<HomepageActivity>(
//                                    "from" to "category"))
//                        } else -> {
//                            context.toast("Gagal Menambah Category")
//                        }
//                    }
//                })
//
//
//
//            }
//
//        }
//
//        close_img.onClick {
//            dismiss()
//        }
//
//    }

}