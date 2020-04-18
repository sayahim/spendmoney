package com.himorfosis.kelolabelanja.category.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.category.model.AssetsModel

class AssetsAdapter : RecyclerView.Adapter<AssetsAdapter.ViewHolder>() {

    private var listData: MutableList<AssetsModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // val name_tv = itemView.findViewById(R.id.name_tv) as TextView
    }

    private fun add(data: AssetsModel) {
        listData!!.add(data)
        notifyItemInserted(listData!!.size - 1)
    }

    fun addAll(posItems: List<AssetsModel>) {
        for (response in posItems) {
            add(response)
        }
    }

}