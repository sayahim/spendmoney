package com.himorfosis.kelolabelanja.month_picker.java;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MonthPickerJavaViewModel extends ViewModel {

    private MonthPickerJavaLiveData liveData;

    private MutableLiveData<String> dataMonth;


    public MutableLiveData<String> getDataMonth() {
        return dataMonth;
    }

    public void setDataMonth(Context context) {

        // set client
        liveData = MonthPickerJavaLiveData.getInstance();

        // set params value
        dataMonth = liveData.setDateSelected(context);

    }
}
