package com.himorfosis.kelolabelanja.homepage.statistict.adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.himorfosis.kelolabelanja.R;
import com.himorfosis.kelolabelanja.homepage.statistict.model.FinancialProgressStatisticModel;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class FinancialProgressAdapter extends RecyclerView.Adapter<FinancialProgressAdapter.ViewHolder> {

    private ArrayList<FinancialProgressStatisticModel> list;
    int progressStatusCounter = 0;
    Handler progressHandler = new Handler();


    public FinancialProgressAdapter(ArrayList<FinancialProgressStatisticModel> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_progress_financial, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        FinancialProgressStatisticModel data = list.get(position);

        holder.title_category_tv.setText(data.getCategory_name());
        holder.total_nominal_tv.setText("Rp " + String.valueOf(data.getTotal_nominal_category()));

        new Thread(new Runnable() {
            public void run() {
                while (progressStatusCounter < data.getMax_nominal()) {

                    progressHandler.post(new Runnable() {
                        public void run() {

                            // set progress
                            holder.total_progress.setProgress(data.getTotal_nominal_category());

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

        public ViewHolder(View itemView) {
            super(itemView);

            total_nominal_tv = (TextView) itemView.findViewById(R.id.total_nominal_tv);
            title_category_tv = (TextView) itemView.findViewById(R.id.title_category_tv);
            total_progress = (ProgressBar) itemView.findViewById(R.id.total_progress);

        }
    }

}
