package com.himorfosis.kelolabelanja.homepage.report.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.homepage.report.model.ReportFinanceModel
import com.himorfosis.kelolabelanja.homepage.report.model.ReportsSpendingModel
import com.himorfosis.kelolabelanja.homepage.statistict.model.FinancialProgressStatisticModel
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_progress_financial.view.*
import java.util.ArrayList

class ReportsAdapter(private val context: Context)
    : RecyclerView.Adapter<ReportsAdapter.ViewHolder>() {

    val TAG = "ReportsAdapter"

    internal var progressStatusCounter = 0
    internal var progressHandler = Handler()
    private var listData: MutableList<ReportsSpendingModel>? = ArrayList<ReportsSpendingModel>()

    override fun getItemCount() = listData!!.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_progress_financial, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = listData!!.get(position)

        val maxNominal = Util.getDataInt("report", "income", context)


        Util.log(TAG, "name : " + data.category_name)

        holder.title_category_tv.setText(data.category_name)
        holder.total_nominal_tv.setText("Rp " + data.total_nominal_category.toString())

        Thread(Runnable {
            while (progressStatusCounter < maxNominal) {
                progressHandler.post {
                    // set progress
                    holder.total_progress.setProgress(data.total_nominal_category)

                    //Status update in textview
                    //                            textView.setText("Status: " + progressStatusCounter + "/" + androidProgressBar.getMax());
                }
                try {
                    Thread.sleep(300)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }).start()

    }

    private fun add(data: ReportsSpendingModel) {
        listData?.add(data)

        notifyItemInserted(listData!!.size - 1)
    }

    fun addAll(posItems: List<ReportsSpendingModel>) {
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

    }
}