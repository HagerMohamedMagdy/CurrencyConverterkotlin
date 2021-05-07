package challengue.swensonhe.com.currencyconverter.viewModels


import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import challengue.swensonhe.com.currencyconverter.model.RateResponse
import challengue.swensonhe.com.currencyconverter.remote.network.RetrofitClient
import challengue.swensonhe.com.currencyconverter.remote.service.GetRateUseCase
import challengue.swensonhe.com.currencyconverter.remote.service.GeteRateService
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver

/**
 * Created by Hager Magdy on 2021-05-08.
 */
class CurrencyRateViewModel( private val getRateUseCase: GetRateUseCase): ViewModel() {

// var rateService:GeteRateService=RetrofitClient.retrofit.create(GeteRateService::class.java)

    val latestRateLiveData: MutableLiveData<RateResponse> = MutableLiveData()

    fun getLatestRate(){

        getRateUseCase.execute(LatestRateSubscriber())


    }
    //Observer
    fun ObserveOnLatestCurrencyRate(owner: LifecycleOwner, observer: Observer<RateResponse>) {
        latestRateLiveData.observe(owner, observer)

    }
    inner class LatestRateSubscriber : DisposableObserver<RateResponse>() {
        override fun onComplete() {

        }

        override fun onNext(rate: RateResponse) {
            Log.e("API","Succes")
            latestRateLiveData.postValue(rate)
        }

        override fun onError(e: Throwable) {Log.e("API","Fail")


        }

    }



}