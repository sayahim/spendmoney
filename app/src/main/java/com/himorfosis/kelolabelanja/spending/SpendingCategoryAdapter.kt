package com.himorfosis.kelolabelanja.spending

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.database.entity.SpendingEntitiy
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_category_spending.view.*

class SpendingCategoryAdapter(var context: Context, var list: List<CategoryEntity>, val adapterCallback : (Any) -> Unit)
    : RecyclerView.Adapter<SpendingCategoryAdapter.ViewHolder>() {

    val TAG = "SpendingCategoryAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, layoutType: Int): ViewHolder {

        var v = LayoutInflater.from(context).inflate(R.layout.item_category_spending, parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = list.get(position)


        holder.nama.text = data.name

//        if (data.image_category != null) {


        val imageAssets = Util.convertImageDrawable(context, data.image_category)

            holder.gambar.setImageResource(imageAssets)

//        } else {
//
//            holder.gambar.setImageResource(R.drawable.ic_broken_image)
//
//        }

        holder.itemView.setOnClickListener {

            Util.log(TAG, "click")

            Util.saveData("category", "selected", data.id.toString(), context)

            // callback adapter
            adapterCallback(data)

        }

        var getIdSelected = Util.getData("category", "selected", context)
        var convertID = data.id.toString()

        if (getIdSelected.equals(convertID)) {

            holder.frame.setBackgroundResource(R.drawable.border_selected)

        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val gambar = itemView.image_img
        val nama = itemView.name_tv
        val frame = itemView.image_frame


    }

}