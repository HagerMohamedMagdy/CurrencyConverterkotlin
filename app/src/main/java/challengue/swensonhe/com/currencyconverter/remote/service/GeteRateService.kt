package challengue.swensonhe.com.currencyconverter.remote.service

import challengue.swensonhe.com.currencyconverter.model.RateResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Hager Magdy on 2020-07-10.
 */
interface GeteRateService {

    @GET(RestApiConstant.getLatestRateAPI+RestApiConstant.ACCESS_KEY)
    fun getLatestRateList(): Observable<RateResponse>

}