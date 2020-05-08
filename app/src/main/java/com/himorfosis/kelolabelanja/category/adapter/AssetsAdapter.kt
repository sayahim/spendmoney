package com.himorfosis.kelolabelanja.category.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.model.AssetsModel
import com.himorfosis.kelolabelanja.category.repo.AssetsCallback
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.CategoryPref
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_assets.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class AssetsAdapter : RecyclerView.Adapter<AssetsAdapter.ViewHolder>() {

    private var listData: List<AssetsModel.Asset> = ArrayList()
    private lateinit var onClickItem: AssetsCallback.OnClickItemAssets
    val getIdSelected = DataPreferences.category.getString(CategoryPref.SELECTED)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_assets, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listData[position]
        val context = holder.itemView.context

        item.let { data ->

            isLog("name : ${data.id_assets_category}")
            // check selected
            if (getIdSelected!!.isNotEmpty()) {
                if (getIdSelected == data.id.toString()) {
                    holder.assets_img.setBackgroundResource(R.drawable.circle_gold)
                } else {
                    holder.assets_img.setBackgroundResource(R.drawable.border_line)
                }
            } else {
                holder.assets_img.setBackgroundResource(R.drawable.border_line)
            }

            Glide.with(context)
                    .load(data.image_assets_url)
                    .thumbnail(0.1f)
                    .error(R.drawable.ic_broken_image)
                    .into(holder.assets_img)

            holder.itemView.onClick {
                holder.assets_img.setBackgroundResource(R.drawable.circle_gold)
                onClickItem = holder.itemView.context as AssetsCallback.OnClickItemAssets
                onClickItem.onItemClicked(data)
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val assets_img = itemView.assets_img
    }

    fun addAll(data: List<AssetsModel.Asset>) {
        listData = data
    }

    fun isLog(msg: String) {
        Log.e("Asset Adapter", msg)
    }

}