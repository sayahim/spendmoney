package com.himorfosis.kelolabelanja.homepage.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.profile.model.ProfileListModel
import kotlinx.android.synthetic.main.item_profile_list.view.*

class ProfileListAdapter : RecyclerView.Adapter<ProfileListAdapter.ViewHolder>() {

    lateinit var onClickItem: OnClickItem
    var listData: MutableList<ProfileListModel> = ArrayList<ProfileListModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_profile_list, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = listData[position]

        if (data != null) {

            holder.title_tv.text = data.name

            if (position == listData.size - 1) {
                holder.title_tv.setTextColor(holder.itemView.resources.getColor(R.color.text_red))
//                holder.indicator_img.visibility = View.GONE
//                holder.icon_img.visibility = View.GONE
            }

            holder.itemView.setOnClickListener {
                onClickItem.onItemClicked(data)
            }

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // val name_tv = itemView.findViewById(R.id.name_tv) as TextView
        val indicator_img = itemView.indicator_img
        val icon_img = itemView.icon_img
        val title_tv = itemView.title_tv

    }

    private fun add(data: ProfileListModel) {

        listData.add(data)
        notifyItemInserted(listData.size - 1)
    }

    fun addAll(posItems: List<ProfileListModel>) {
        for (response in posItems) {
            add(response)
        }
    }


    interface OnClickItem {
        fun onItemClicked(data: ProfileListModel)
    }

    fun setOnclick(onClickItem: OnClickItem) {
        this.onClickItem = onClickItem
    }


}