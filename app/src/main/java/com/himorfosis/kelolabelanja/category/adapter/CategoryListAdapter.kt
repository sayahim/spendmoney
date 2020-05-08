package com.himorfosis.kelolabelanja.category.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.model.CategoryResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_category_list.view.*

class CategoryListAdapter : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    lateinit var onClickItem: OnClickItem

    var listData: MutableList<CategoryResponse> = ArrayList<CategoryResponse>()

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
            holder.category_name_tv.text = data.title

//            val imageAssets = Util.convertImageDrawable(holder.itemView.context, data.image_category)
//            holder.category_img.setImageResource(imageAssets)
            Picasso.with(holder.itemView.context)
                    .load(data.image_category_url)
                    .error(R.drawable.ic_broken_image)
                    .into(holder.category_img)

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

    private fun add(data: CategoryResponse) {

        listData.add(data)
        notifyItemInserted(listData.size - 1)

    }

    fun addAll(posItems: List<CategoryResponse>) {
        for (response in posItems) {

            add(response)

        }
    }

    interface OnClickItem {
        fun onItemClicked(data: CategoryResponse)
    }

    fun setOnclick(onClickItem: OnClickItem) {
        this.onClickItem = onClickItem
    }


}