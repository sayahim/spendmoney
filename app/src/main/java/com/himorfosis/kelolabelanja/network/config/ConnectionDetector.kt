package com.himorfosis.kelolabelanja.network.config

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.himorfosis.kelolabelanja.utilities.Util

class ConnectionDetector {

    companion object {
        fun isConnectingToInternet(context: Context):Boolean {
            val service = Context.CONNECTIVITY_SERVICE
            val manager = context.getSystemService(service) as ConnectivityManager?
            val network = manager?.activeNetworkInfo
            Util.log("ConnectionDetector", "isConnectingToInternet: ${(network != null)}")
            return (network != null)
        }


    }

}