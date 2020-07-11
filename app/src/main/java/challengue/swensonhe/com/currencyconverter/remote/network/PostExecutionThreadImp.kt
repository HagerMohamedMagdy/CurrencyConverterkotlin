package challengue.swensonhe.com.currencyconverter.remote.network


import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class PostExecutionThreadImp : PostExecutionThread {
        override val scheduler: Scheduler
            get() = AndroidSchedulers.mainThread()
}