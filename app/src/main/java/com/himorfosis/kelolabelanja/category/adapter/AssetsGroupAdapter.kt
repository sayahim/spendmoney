package com.himorfosis.kelolabelanja.category.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.model.AssetsModel
import kotlinx.android.synthetic.main.item_assets_group.view.*
import kotlin.properties.Delegates

class AssetsGroupAdapter : RecyclerView.Adapter<AssetsGroupAdapter.ViewHolder>() {

//    private var listData: MutableList<AssetsModel> = ArrayList()

    var listData: List<AssetsModel> by Delegates.observable(emptyList()) { _, oldList, newList ->
        notifyChanges(oldList, newList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_assets_group, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listData[position]
        item.let {
            holder.title_assets_tv.text = it.title
            val adapterAssets = AssetsAdapter()
            holder.recycler_assets_group.apply {
                layoutManager = GridLayoutManager(holder.itemView.context, 3)
                adapter = adapterAssets
                adapterAssets.addAll(item.assets)
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title_assets_tv = itemView.title_assets_tv
        val recycler_assets_group = itemView.recycler_assets_group
    }

    private fun notifyChanges(oldList: List<AssetsModel>, newList: List<AssetsModel>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].title == newList[newItemPosition].title
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size
        })
        diff.dispatchUpdatesTo(this)
    }

    fun addAll(data: List<AssetsModel>) {
        listData = data
    }

    private fun isLog(msg: String) {
        Log.e("assets group adapter", msg)
    }

}