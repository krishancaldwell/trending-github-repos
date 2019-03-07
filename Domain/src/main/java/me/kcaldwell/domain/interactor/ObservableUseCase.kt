package me.kcaldwell.domain.interactor

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import me.kcaldwell.domain.executor.PostExecutionThread

abstract class ObservableUseCase<T, in Params> constructor(
    private val postExecutionThread: PostExecutionThread) {

    private val mDisposables = CompositeDisposable()

    protected abstract fun buildUseCaseObservable(params: Params? = null): Observable<T>

    open fun execute(observer: DisposableObserver<T>, params: Params? = null) {
        val observable = this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.mScheduler)
        addDisposable(observable.subscribeWith(observer))
    }

    fun dispose() {
        mDisposables.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        mDisposables.add(disposable)
    }

}