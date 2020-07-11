 package challengue.swensonhe.com.currencyconverter.remote.network

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class ObservableUseCase<T, in E>(private val schedulerThread: PostExecutionThread) {
    private val disposables: CompositeDisposable = CompositeDisposable()
    abstract fun buildUseCaseObservable(params: E? = null): Observable<T>

    open fun execute(observer: DisposableObserver<T>, params: E? = null) {
        val observable = getObservable(params)
        addDisposable(observable.subscribeWith(observer))
    }

    fun dispose() {
        disposables.dispose()
    }

    fun disposeLast() {

    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun getObservable(params: E? = null): Observable<T> {
        return buildUseCaseObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(schedulerThread.scheduler)
    }


}