
package challengue.swensonhe.com.currencyconverter.remote.service
import challengue.swensonhe.com.currencyconverter.model.RateResponse
import challengue.swensonhe.com.currencyconverter.remote.network.ObservableUseCase
import challengue.swensonhe.com.currencyconverter.remote.network.PostExecutionThread
import challengue.swensonhe.com.currencyconverter.remote.service.RateRepositry
import io.reactivex.Observable


/**
 * Created by Hager Magdy on
 */

class GetRateUseCase(
    private val rateRepository: RateRepositry
    , postExecutionThread: PostExecutionThread
) : ObservableUseCase<RateResponse, Unit>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Unit?): Observable<RateResponse> {

        return rateRepository.getLatestRate()

    }






}