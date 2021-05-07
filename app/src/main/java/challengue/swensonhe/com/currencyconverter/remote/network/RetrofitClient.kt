package challengue.swensonhe.com.currencyconverter.remote.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Hager Magdy on 2021-05-08.
 */
object RetrofitClient {

    var gson= GsonBuilder().setLenient().create()
    private const val BASE_URL = "http://data.fixer.io/api/"

    val retrofit: Retrofit by lazy {
        createRetrofitInstance()
    }
    private fun createRetrofitInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createInterceptor())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    private fun createInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(7000, TimeUnit.SECONDS)
           // .addInterceptor(AuthenticationInterceptor())
            .addInterceptor(interceptor).build()
    }
}