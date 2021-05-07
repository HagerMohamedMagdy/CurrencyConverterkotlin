package challengue.swensonhe.com.currencyconverter.remote.service

import challengue.swensonhe.com.currencyconverter.model.Rate
import challengue.swensonhe.com.currencyconverter.model.RateResponse
import challengue.swensonhe.com.currencyconverter.model.Rates
import io.reactivex.Observable

/**
 * Created by Hager Magdy on 2021-05-08.
 */
class RateRepositry (private val geteRateService: GeteRateService){
    fun getLatestRate(): Observable<RateResponse> {

return geteRateService.getLatestRateList()

}

}