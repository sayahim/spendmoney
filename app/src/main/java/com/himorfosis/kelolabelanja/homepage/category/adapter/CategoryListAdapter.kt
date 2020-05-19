package com.himorfosis.kelolabelanja.homepage.category.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.model.CategoryResponse
import kotlinx.android.synthetic.main.item_category_list.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class CategoryListAdapter : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    lateinit var onClickItem: OnClickItem
    var listData: MutableList<CategoryResponse> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_category_list, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]

        data.let {
            holder.category_name_tv.text = it.title

            Glide.with(holder.itemView.context)
                    .load(it.image_category_url)
                    .thumbnail(0.1f)
                    .error(R.drawable.ic_broken_image)
                    .into(holder.category_img)

            holder.delete_img.setOnClickListener {
                onClickItem.onItemHide(data)
            }

            holder.itemView.onClick {
                onClickItem.onItemClicked(data)
            }
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val category_img = itemView.category_img
        val category_name_tv = itemView.category_name_tv
        val delete_img = itemView.delete_img
    }

    private fun add(data: CategoryResponse) {
        listData.add(data)
        notifyItemInserted(listData.size - 1)

    }

    fun addAll(data: List<CategoryResponse>) {
        data.forEach {
            add(it)
        }
//        for (response in posItems) {
//            add(response)
//        }
    }

    interface OnClickItem {
        fun onItemClicked(data: CategoryResponse)
        fun onItemHide(data: CategoryResponse)
    }

    fun setOnclick(onClickItem: OnClickItem) {
        this.onClickItem = onClickItem
    }


}