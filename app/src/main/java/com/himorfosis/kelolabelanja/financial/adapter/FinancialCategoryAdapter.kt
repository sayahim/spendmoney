package com.himorfosis.kelolabelanja.financial.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.model.CategoryResponse
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.AppPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.CategoryPref
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_category_financials.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.ArrayList

class FinancialCategoryAdapter : RecyclerView.Adapter<FinancialCategoryAdapter.ViewHolder>() {

    private val TAG = "FinancialCategoryAdapter"

//    private var listFilter: MutableList<CategoryResponse> = ArrayList()
    private var listData: List<CategoryResponse> = ArrayList()

    lateinit var onClickItem: AdapterOnClickItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category_financials, parent, false))
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = listData[position]
        val getIdSelected = DataPreferences.category.getString(CategoryPref.SELECTED)
        // check selected
        if (getIdSelected!!.isNotEmpty()) {
            if (getIdSelected == data.id) {
                holder.frame.setBackgroundResource(R.drawable.circle_gold)
            } else {
                holder.frame.setBackgroundResource(R.drawable.border_line)
            }
        } else {
            holder.frame.setBackgroundResource(R.drawable.border_line)
        }

        holder.name_tv.text = data.title

        data.image_category_url.let {
            Glide.with(holder.itemView.context)
                    .load(it)
                    .thumbnail(0.1f)
                    .error(R.drawable.ic_broken_image)
                    .into(holder.image_img)
        }

        holder.itemView.onClick {
            holder.frame.setBackgroundResource(R.drawable.circle_gold)
            DataPreferences.category.saveString(CategoryPref.SELECTED, data.id)
            notifyDataSetChanged()
            Util.log(TAG, "id selected  : " + data.id)
            // callback adapter
            onClickItem.onItemClicked(data)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image_img = itemView.image_img
        val name_tv = itemView.name_tv
        val frame = itemView.image_frame
    }

    interface AdapterOnClickItem {
        fun onItemClicked(data: CategoryResponse)
    }

    fun setOnclick(onClickItem: AdapterOnClickItem) {
        this.onClickItem = onClickItem
    }

//    private fun add(data: CategoryResponse) {
//        listData!!.add(data)
//        listFilter!!.add(data)
//    }

    fun submitList(data: List<CategoryResponse>) {
        isLog("submit list")
        listData = data
        notifyDataSetChanged()
        isLog("list size : ${listData.size}")
//        data.forEach {
//            add(it)
//        }
    }

    private fun isLog(message: String) {
        Util.log("CategoryAdapter", message)
    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(charSequence: CharSequence): FilterResults {
//                val charString = charSequence.toString()
//                Util.log(TAG, "charString : $charString")
//                if (charString.isEmpty()) {
//                    listData = listFilter
//                } else {
//                    val filteredList = ArrayList<CategoryResponse>()
//                    listFilter.forEach {
//                        if (it.title.toLowerCase().contains(charString.toLowerCase()) ||
//                                it.title.contains(charSequence)) {
//                            filteredList.add(it)
//                        }
//                    }
//
//                    listData = filteredList
//                }
//                val filterResults = FilterResults()
//                filterResults.values = listData
//                return filterResults
//            }
//
//            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
//                Util.log(TAG, "list filter : $listData")
//                if (listData != null) {
//                    listData = filterResults.values as MutableList<CategoryResponse>
//                    notifyDataSetChanged()
//                }
//            }
//        }
//    }

}