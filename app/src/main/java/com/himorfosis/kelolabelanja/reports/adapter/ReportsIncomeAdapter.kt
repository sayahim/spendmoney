package com.himorfosis.kelolabelanja.reports.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.reports.model.ReportsDataModel
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_progress_financial.view.*
import java.util.ArrayList

class ReportsIncomeAdapter(private val context: Context)
    : RecyclerView.Adapter<ReportsIncomeAdapter.ViewHolder>() {

    val TAG = "ReportsIncomeAdapter"

    internal var progressStatusCounter = 0
    internal var progressHandler = Handler()
    private var listData: MutableList<ReportsDataModel> = ArrayList<ReportsDataModel>()
    val maxNominal = Util.getDataInt("report", "income", context)

    override fun getItemCount() = listData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_progress_financial, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = listData[position]

        holder.title_category_tv.text = data.category_name
        holder.total_nominal_tv.text = "Rp " + data.total_nominal_category.toString()

        // set image
        val imageAssets = Util.convertImageDrawable(context, data.category_image)
        holder.category_image.setImageResource(imageAssets)

        // count to get persen
        val progressPersen:Double = data.total_nominal_category.toDouble() / maxNominal.toDouble()
        val persen:Double = progressPersen * 100.0

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

    }

    private fun add(data: ReportsDataModel) {
        listData?.add(data)

        notifyItemInserted(listData!!.size - 1)
    }

    fun addAll(posItems: List<ReportsDataModel>) {
        for (response in posItems) {

            add(response)
        }
    }

    fun removeListAdapter() {

        listData?.clear()

        notifyDataSetChanged()

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val total_nominal_tv = itemView.total_nominal_tv
        val title_category_tv = itemView.title_category_tv
        val total_progress = itemView.total_progress
        val category_image = itemView.category_img

    }
}