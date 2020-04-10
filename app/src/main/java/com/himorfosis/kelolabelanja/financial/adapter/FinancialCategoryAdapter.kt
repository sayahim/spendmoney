package com.himorfosis.kelolabelanja.financial.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.model.CategoryEntity
import com.himorfosis.kelolabelanja.response.Category.CategoryResponse
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_category_financials.view.*
import java.util.ArrayList

class FinancialCategoryAdapter : RecyclerView.Adapter<FinancialCategoryAdapter.ViewHolder>(), Filterable {

    private val TAG = "FinancialCategoryAdapter"

    private var dataListFilter: MutableList<CategoryResponse> = ArrayList<CategoryResponse>()
    private var listData: MutableList<CategoryResponse> = ArrayList<CategoryResponse>()

    lateinit var onClickItem: AdapterOnClickItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category_financials, parent, false))
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val getIdSelected = Util.getData("category", "selected", holder.itemView.context)

        var data = listData[position]

        // check selected

        var convertID = data.id.toString()

        if (getIdSelected != null) {

            if (getIdSelected == convertID) {

                holder.frame.setBackgroundResource(R.drawable.circle_gold)
            } else {

                holder.frame.setBackgroundResource(R.drawable.border_line)
            }

        } else {

            holder.frame.setBackgroundResource(R.drawable.border_line)

        }

        val imageAssets = Util.convertImageDrawable(holder.itemView.context, data.image_category)

        holder.name_tv.text = data.title

        holder.image_img.setImageResource(imageAssets)

        holder.itemView.setOnClickListener {
            holder.frame.setBackgroundResource(R.drawable.circle_gold)
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

    private fun add(data: CategoryResponse) {
        listData!!.add(data)

        // add data filter
        dataListFilter = listData

        notifyItemInserted(listData!!.size - 1)
    }

    fun addAll(posItems: List<CategoryResponse>) {
        for (response in posItems) {

            dataListFilter = listData

            add(response)
        }
    }

    fun removeAllData() {

        if (listData.isNotEmpty()) {

            listData.clear()
            notifyDataSetChanged()
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

                val charString = charSequence.toString()

                Util.log(TAG, "charString : $charString")

                if (charString.isEmpty()) {

                    listData = dataListFilter

                } else {

                    val filteredList = ArrayList<CategoryResponse>()
                    for (row in dataListFilter.orEmpty()) {

                        // check data search
                        if (row.title.toLowerCase().contains(charString.toLowerCase()) || row.title.contains(charSequence)) {
                            filteredList.add(row)
                        }
                    }

                    listData = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = listData
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {

                Util.log(TAG, "list filter : " + listData)

                if (listData != null) {

                    listData = filterResults.values as MutableList<CategoryResponse>
                    notifyDataSetChanged()
                }

            }
        }
    }

}