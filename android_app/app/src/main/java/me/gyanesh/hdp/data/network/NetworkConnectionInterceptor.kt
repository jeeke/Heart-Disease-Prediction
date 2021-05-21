package me.gyanesh.hdp.data.network

import android.content.Context
import android.net.ConnectivityManager
import me.gyanesh.hdp.R
import me.gyanesh.hdp.util.NoInternetExcepetion
import me.gyanesh.hdp.util.getString
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context?) : Interceptor {
    private val applicationContext = context?.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isInternetAvailable())
            throw NoInternetExcepetion(getString(R.string.no_internet))
        return chain.proceed(chain.request())
    }


    private fun isInternetAvailable() : Boolean {
        val connectivityManager =
            applicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}