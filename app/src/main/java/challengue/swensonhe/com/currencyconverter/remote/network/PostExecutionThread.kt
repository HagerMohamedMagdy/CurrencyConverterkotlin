package challengue.swensonhe.com.currencyconverter.remote.network

import io.reactivex.Scheduler


interface PostExecutionThread {
    val scheduler: Scheduler
}