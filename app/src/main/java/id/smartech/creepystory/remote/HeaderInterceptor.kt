package id.smartech.creepystory.remote

import android.content.Context
import id.smartech.creepystory.util.SharedPreference
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val context: Context) : Interceptor {
    private lateinit var sharedPref: SharedPreference

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        sharedPref = SharedPreference(context)

        proceed(
            request().newBuilder()
                .addHeader("Authorization", "Bearer ${sharedPref.getToken()}")
                .build()
        )
    }
}