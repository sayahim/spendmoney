package com.himorfosis.kelolabelanja.homepage.chart.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.homepage.chart.model.ChartCategoryModel
import com.himorfosis.kelolabelanja.reports.model.ReportsDataModel
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_progress_financial.view.*
import java.util.ArrayList

class ReportChartAdapter : RecyclerView.Adapter<ReportChartAdapter.ViewHolder>() {

    private var listData: MutableList<ChartCategoryModel> = ArrayList<ChartCategoryModel>()

    lateinit var onClickItem: OnClickItem

    // progress
    internal var progressStatusCounter = 0
    internal var progressHandler = Handler()
    var maxNominal: Long = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_progress_financial, parent, false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = listData[position]

        if (data != null) {

            holder.title_category_tv.text = data.category_name
            holder.total_nominal_tv.text = "Rp " + data.total_nominal_category.toString()

            // set image
            val imageAssets = Util.convertImageDrawable(holder.itemView.context, data.category_image)
            holder.category_image.setImageResource(imageAssets)

            // count to get persen
            val progressPersen: Double = data.total_nominal_category!!.toDouble() / maxNominal.toDouble()
            val persen: Double = progressPersen * 100.0

            Thread(Runnable {
                while (progressStatusCounter < 100) {
                    progressHandler.post {
                        // set progress
                        holder.total_progress.progress = persen.toInt()

                    }
                    try {
                        Thread.sleep(300)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }).start()

            holder.itemView.setOnClickListener {
                onClickItem.onItemClicked(data)
            }

        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val total_nominal_tv = itemView.total_nominal_tv
        val title_category_tv = itemView.title_category_tv
        val total_progress = itemView.total_progress
        val category_image = itemView.category_img
    }

    private fun add(data: ChartCategoryModel) {
        listData.add(data)
        notifyItemInserted(listData.size - 1)
    }

    fun addAll(posItems: List<ChartCategoryModel>, getMaxNominal: Long) {

        maxNominal = getMaxNominal

        for (response in posItems) {
            add(response)
        }
    }

    fun removeAdapter() {

        if (listData.isNotEmpty()) {
            listData.clear()
            notifyDataSetChanged()
        }
    }

    interface OnClickItem {
        fun onItemClicked(data: ChartCategoryModel)
    }

    fun setOnclick(onClickItem: OnClickItem) {
        this.onClickItem = onClickItem
    }

}