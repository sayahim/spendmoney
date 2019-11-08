package com.himorfosis.kelolabelanja.category

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_category_spending.view.*
import java.util.ArrayList

class CategoryAdapter(private val context: Context, val adapterCallback: (CategoryEntity) -> Unit)
    : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(), Filterable {

    private val TAG = "CategoryAdapter"

    private var dataListFilter: MutableList<CategoryEntity>? = null
    private var listData: MutableList<CategoryEntity>? = ArrayList<CategoryEntity>()

    val getIdSelected = Util.getData("category", "selected", context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category_spending, parent, false))
    }

    override fun getItemCount() = listData!!.size

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {

        var data = listData!!.get(position)

        // check selected

        var convertID = data.id.toString()

        if (getIdSelected.equals(convertID)) {

            Util.log(TAG, "pos : " + position)
            Util.log(TAG, "id selected : " + getIdSelected)
            Util.log(TAG, "id  : " + convertID)

            holder.frame.setBackgroundResource(R.drawable.border_selected)

        } else {

            holder.frame.setBackgroundResource(R.drawable.border_line)

        }

        val imageAssets = Util.convertImageDrawable(context, data.image_category)

        holder.name_tv.text = data.name

        holder.image_img.setImageResource(imageAssets)

        holder.itemView.setOnClickListener {

            Util.log(TAG, "id selected  : " + data.id)

            // callback adapter
            adapterCallback(data)

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image_img = itemView.image_img
        val name_tv = itemView.name_tv
        val frame = itemView.image_frame

    }

    private fun add(categoryEntity: CategoryEntity) {
        listData!!.add(categoryEntity)

        // add data filter
        dataListFilter = listData

        notifyItemInserted(listData!!.size - 1)
    }

    fun addAll(posItems: List<CategoryEntity>) {
        for (response in posItems) {

            dataListFilter = listData

            add(response)
        }
    }

    fun removeListAdapter() {

        listData!!.clear()

        notifyDataSetChanged()

    }

    interface AdapterOnClick {
        fun onClick(item: Any)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

                val charString = charSequence.toString()

                Util.log(TAG, "charString : $charString")

                if (charString.isEmpty()) {

                    listData = dataListFilter

                } else {

                    val filteredList = ArrayList<CategoryEntity>()
                    for (row in dataListFilter.orEmpty()) {

                        // check data search
                        if (row.name.toLowerCase().contains(charString.toLowerCase()) || row.name.contains(charSequence)) {
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

                    listData = filterResults.values as MutableList<CategoryEntity>
                    notifyDataSetChanged()
                }

            }
        }
    }

}