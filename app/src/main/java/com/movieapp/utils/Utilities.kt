package com.movieapp.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.FragmentActivity

object Utilities {

    fun isOnline(activity: FragmentActivity?): Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}