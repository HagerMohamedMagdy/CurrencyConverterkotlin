package challengue.swensonhe.com.currencyconverter

import android.app.Application
import challengue.swensonhe.com.currencyconverter.remote.network.PostExecutionThread
import challengue.swensonhe.com.currencyconverter.remote.network.PostExecutionThreadImp
import challengue.swensonhe.com.currencyconverter.remote.network.RetrofitClient
import challengue.swensonhe.com.currencyconverter.remote.service.GetRateUseCase
import challengue.swensonhe.com.currencyconverter.remote.service.GeteRateService
import challengue.swensonhe.com.currencyconverter.remote.service.RateRepositry
import challengue.swensonhe.com.currencyconverter.utils.ConnectionLiveData
import challengue.swensonhe.com.currencyconverter.viewModels.CurrencyRateViewModel

/**
 * Created by Hager Magdy on 2021-05-08.
 */
class CurrencyApplication : Application() {
    companion object {
    private lateinit var latestRateViewModel: CurrencyRateViewModel
        private lateinit var getRateUseCase: GetRateUseCase
        private lateinit var rateRepositry: RateRepositry
        lateinit var postExecutionThread: PostExecutionThread
       private lateinit var geteRateService:GeteRateService
    fun injectCurrencyViewModel() = latestRateViewModel


    }

    override fun onCreate() {
        super.onCreate()
       postExecutionThread= PostExecutionThreadImp()
        geteRateService = RetrofitClient.retrofit.create(GeteRateService::class.java)
        rateRepositry= RateRepositry(geteRateService)
        getRateUseCase = GetRateUseCase(rateRepositry, postExecutionThread)
        latestRateViewModel = CurrencyRateViewModel(getRateUseCase)
        ConnectionLiveData.init(this)
    }
}