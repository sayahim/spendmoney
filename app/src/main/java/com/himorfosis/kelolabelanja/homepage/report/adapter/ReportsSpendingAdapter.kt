package com.himorfosis.kelolabelanja.homepage.report.adapter

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.details.category.FinancialCategoryDetail
import com.himorfosis.kelolabelanja.homepage.report.model.ReportsSpendingModel
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_progress_financial.view.*
import java.util.ArrayList

class ReportsSpendingAdapter (private val context: Context)
    : RecyclerView.Adapter<ReportsSpendingAdapter.ViewHolder>() {

    val TAG = "ReportsSpendingAdapter"

    internal var progressStatusCounter = 0
    internal var progressHandler = Handler()
    private var listData: MutableList<ReportsSpendingModel> = ArrayList<ReportsSpendingModel>()
    val maxNominal = Util.getDataInt("report", "spend", context)

    override fun getItemCount() = listData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_progress_financial, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = listData[position]

        Util.log(TAG, "max nominal : $maxNominal")
        Util.log(TAG, "category : ${data.category_name}")
        Util.log(TAG, "nominal : ${data.total_nominal_category}")

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
                    Thread.sleep(20)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }).start()


        holder.itemView.setOnClickListener {

            val intent = Intent(context, FinancialCategoryDetail::class.java)
            intent.putExtra("category", data.id_category.toString())
            intent.putExtra("title_category", data.category_name)
            intent.putExtra("type", "spend")
            context.startActivity(intent)

        }


    }

    private fun add(data: ReportsSpendingModel) {
        listData.add(data)
        notifyItemInserted(listData.size - 1)
    }

    fun addAll(posItems: List<ReportsSpendingModel>) {
        for (response in posItems) {

            add(response)
        }
    }

    fun removeListAdapter() {

        listData.clear()

        notifyDataSetChanged()

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val total_nominal_tv = itemView.total_nominal_tv
        val title_category_tv = itemView.title_category_tv
        val total_progress = itemView.total_progress
        val category_image = itemView.category_img

    }
}