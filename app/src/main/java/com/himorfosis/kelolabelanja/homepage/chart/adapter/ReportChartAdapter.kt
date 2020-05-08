package com.himorfosis.kelolabelanja.homepage.chart.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.homepage.chart.model.ReportCategoryModel
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_progress_financial.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.ArrayList

class ReportChartAdapter : RecyclerView.Adapter<ReportChartAdapter.ViewHolder>() {

    private var listData: MutableList<ReportCategoryModel> = ArrayList<ReportCategoryModel>()

    lateinit var onClickItem: OnClickItem

    // progress
    internal var progressStatusCounter = 0
    internal var progressHandler = Handler()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_progress_financial, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = listData[position]

        data.let {

            holder.title_category_tv.text = data.title
            holder.total_nominal_tv.text = Util.numberFormatMoney(data.total_nominal.toLong().toString())

            data.image_category_url.let {
                Glide.with(holder.itemView.context)
                        .load(it)
                        .thumbnail(0.1f)
                        .error(R.drawable.ic_broken_image)
                        .into(holder.category_image)
            }

            Thread(Runnable {
                while (progressStatusCounter < 100) {
                    progressHandler.post {
                        // set progress
                        holder.total_progress.progress = data.total_percentage
                    }
                    try {
                        Thread.sleep(300)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }).start()

            holder.itemView.onClick {
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

    private fun add(data: ReportCategoryModel) {
        listData.add(data)
        notifyItemInserted(listData.size - 1)
    }

    fun addAll(posItems: List<ReportCategoryModel>) {
        for (response in posItems) {
            add(response)
        }
    }

    fun clear() {

        if (listData.isNotEmpty()) {
            listData.clear()
            notifyDataSetChanged()
        }
    }

    interface OnClickItem {
        fun onItemClicked(data: ReportCategoryModel)
    }

    fun setOnclick(onClickItem: OnClickItem) {
        this.onClickItem = onClickItem
    }

}