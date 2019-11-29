package com.himorfosis.kelolabelanja.homepage.statistict.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.himorfosis.kelolabelanja.R;
import com.himorfosis.kelolabelanja.homepage.statistict.model.ChartModel;
import com.himorfosis.kelolabelanja.homepage.statistict.model.FinancialProgressStatisticModel;
import com.himorfosis.kelolabelanja.homepage.statistict.model.StatistictModel;
import com.himorfosis.kelolabelanja.utilities.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class StatisticChartAdapter extends RecyclerView.Adapter<StatisticChartAdapter.ViewHolder> {

    String TAG = "StatisticChartAdapter";

    private List<ChartModel> list;
    int progressStatusCounter = 0;
    Handler progressHandler = new Handler();

    Context context;
    Integer maxNominal;


    public StatisticChartAdapter(Context context, List<ChartModel> list) {
        this.list = list;
        this.context = context;
        maxNominal = Util.getDataInt("report", "chart", context);

        Util.log(TAG, "max nominal : " + maxNominal);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_progress_financial, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ChartModel data = list.get(position);

        holder.title_category_tv.setText(data.getCategory_name());
        holder.total_nominal_tv.setText(Util.numberFormatMoney(String.valueOf(data.getTotal_nominal_category())));

        // set image

        int imageAssets = Util.convertImageDrawable(context, data.getCategory_image());
        holder.category_image.setImageResource(imageAssets);

        // count to get persen
        Double progressPersen = new Double(data.getTotal_nominal_category()) / maxNominal.doubleValue();
        Double persen = progressPersen * 100.0;

        Util.log(TAG, "persen : " + persen);

        new Thread(new Runnable() {
            public void run() {
                while (progressStatusCounter < 100) {

                    progressStatusCounter++;

                    progressHandler.post(new Runnable() {
                        public void run() {

                            // set progress
                            holder.total_progress.setProgress(persen.intValue());

                            //Status update in textview
//                            textView.setText("Status: " + progressStatusCounter + "/" + androidProgressBar.getMax());

                        }
                    });
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public void removeAdapter() {

        list.clear();
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView total_nominal_tv, title_category_tv;
        private ProgressBar total_progress;
        private ImageView category_image;

        public ViewHolder(View itemView) {
            super(itemView);

            total_nominal_tv = (TextView) itemView.findViewById(R.id.total_nominal_tv);
            title_category_tv = (TextView) itemView.findViewById(R.id.title_category_tv);
            total_progress = (ProgressBar) itemView.findViewById(R.id.total_progress);
            category_image = (ImageView) itemView.findViewById(R.id.category_img);

        }
    }

}
