package com.himorfosis.kelolabelanja.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UtilJava {

    // save data shared preference String
    public static void saveData(String DBNAME, String Tablekey, String value, Context context) {

        SharedPreferences settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);;
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putString(Tablekey, value);
        editor.commit();
    }


    // get data shared preference String

    public static String getData(String DBNAME, String Tablekey, Context context) {

        SharedPreferences settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        String text;
        text = settings.getString(Tablekey, null);
        return text;
    }

    // delete data table shared preference String

    public static void deleteData(String DBNAME, Context context) {

        SharedPreferences settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);;
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public static String convertCalendarMonth(String month) {

        String[] monthArray = new String[]{
                "", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        };

        String dateMonth = month.substring(month.indexOf("-") + 1);
        String bulan = dateMonth.substring(0, dateMonth.indexOf("-"));

        Integer intMonth = Integer.parseInt(bulan);

        String data = monthArray[intMonth];

        UtilJava.log("convertCalendarMonth", "data : " + data);
        UtilJava.log("convertCalendarMonth", "intMonth : " + intMonth);
        UtilJava.log("convertCalendarMonth", "month : " + month);

        return data;

    }

    public static void log(String TAG, String name) {

        Log.e(TAG, name);

    }

//    fun convertCalendarMonth(month:String) : String {
//
//        val monthArray = arrayOf("", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")
//
//        val dateMonth = month.substring(month.indexOf(".") + 1)
//        val bulan = dateMonth.substring(0, dateMonth.indexOf("."))
//
//        val intMonth = Integer.parseInt(bulan)
//
//        return monthArray[intMonth]
//
//    }
//
}
