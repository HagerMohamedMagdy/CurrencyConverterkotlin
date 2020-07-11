package challengue.swensonhe.com.currencyconverter.remote.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Hager Magdy on 2020-07-10.
 */
class AuthenticationInterceptor:Interceptor {
    companion object {
        private const val ACCESS_KEY = "bff20022786ecd70a58d8522d8f57639"
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestPath = originalRequest.url().url().file
        val requestBuilder = originalRequest.newBuilder()

        return chain.proceed(requestBuilder.build())



    }

}