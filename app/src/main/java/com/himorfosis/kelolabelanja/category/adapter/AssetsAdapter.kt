package com.himorfosis.kelolabelanja.category.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.model.AssetsModel
import com.himorfosis.kelolabelanja.dialog.DialogCategoryInput
import com.himorfosis.kelolabelanja.dialog.DialogLoading
import com.himorfosis.kelolabelanja.dialog.InputCategoryBottomDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_assets.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class AssetsAdapter : RecyclerView.Adapter<AssetsAdapter.ViewHolder>() {

    private var listData: MutableList<AssetsModel.Asset> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_assets, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]
        val context = holder.itemView.context

        if (item != null) {
            Picasso.with(context)
                    .load(item.image_assets_url)
                    .error(R.drawable.ic_broken_image)
                    .into(holder.assets_img)

            holder.itemView.onClick {

                val ft = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                val dialog = DialogCategoryInput(context, item.image_assets, item.image_assets_url)
                dialog.show(ft, "dialog")

//                var dialog = DialogCategoryInput(context, item.image_assets_url)
//                 dialog.show()

            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val assets_img = itemView.assets_img
        val assets_frame = itemView.assets_frame
    }

    private fun add(data: AssetsModel.Asset) {
        listData.add(data)
        notifyItemInserted(listData.size - 1)
    }

    fun addAll(posItems: List<AssetsModel.Asset>) {
        for (response in posItems) {
            add(response)
        }
    }

}