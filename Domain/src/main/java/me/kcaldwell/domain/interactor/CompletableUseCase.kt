package me.kcaldwell.domain.interactor

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import me.kcaldwell.domain.executor.PostExecutionThread

abstract class CompletableUseCase<in Params> constructor(
    private val postExecutionThread: PostExecutionThread) {

    private val mDisposables = CompositeDisposable()

    protected abstract fun buildUseCaseCompletable(params: Params? = null): Completable

    open fun execute(observer: DisposableCompletableObserver, params: Params? = null) {
        val completable = this.buildUseCaseCompletable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.mScheduler)
        addDisposable(completable.subscribeWith(observer))
    }

    fun dispose() {
        mDisposables.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        mDisposables.add(disposable)
    }

}