package com.himorfosis.kelolabelanja.category.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_category_list.view.*

class CategoryListAdapter : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    lateinit var onClickItem: OnClickItem

    var listData: MutableList<CategoryEntity> = ArrayList<CategoryEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_category_list, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]

        if (data != null) {
            holder.category_name_tv.text = data.name

            val imageAssets = Util.convertImageDrawable(holder.itemView.context, data.image_category)
            holder.category_img.setImageResource(imageAssets)

            holder.delete_img.setOnClickListener {
                onClickItem.onItemClicked(data)

            }

        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val category_img = itemView.category_img
        val category_name_tv = itemView.category_name_tv
        val delete_img = itemView.delete_img

        // val name_tv = itemView.findViewById(R.id.name_tv) as TextView

    }

    private fun add(data: CategoryEntity) {

        listData.add(data)
        notifyItemInserted(listData.size - 1)

    }

    fun addAll(posItems: List<CategoryEntity>) {
        for (response in posItems) {

            add(response)

        }
    }


    interface OnClickItem {
        fun onItemClicked(data: CategoryEntity)

    }

    fun setOnclick(onClickItem: OnClickItem) {
        this.onClickItem = onClickItem

    }


}