package com.bertan.domain.interactor

import com.bertan.domain.executor.SchedulerExecutor
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class RxUseCase<Params, Rx> {
    internal val disposables = CompositeDisposable()

    abstract fun buildUseCase(params: Params? = null): Rx

    fun dispose() = disposables::dispose

    fun Params?.validate(block: (Params) -> Rx): Rx =
        this
            ?.let(block)
            ?: throw IllegalArgumentException("Parameter should not be null!")
}

abstract class CompletableUseCase<Params>(private val executor: SchedulerExecutor) :
    RxUseCase<Params, Completable>() {
    fun execute(params: Params? = null, onComplete: () -> Unit, onError: (Throwable) -> Unit) =
        buildUseCase(params)
            .subscribeOn(Schedulers.io())
            .observeOn(executor.scheduler)
            .subscribeWith(
                object : DisposableCompletableObserver() {
                    override fun onComplete() = onComplete()
                    override fun onError(e: Throwable) = onError(e)
                })
            .also { disposables.add(it) }
}

abstract class ObservableUseCase<T, Params>(private val executor: SchedulerExecutor) :
    RxUseCase<Params, Observable<T>>() {
    fun execute(params: Params? = null, onNext: (T) -> Unit, onComplete: () -> Unit, onError: (Throwable) -> Unit) =
        buildUseCase(params)
            .subscribeOn(Schedulers.io())
            .observeOn(executor.scheduler)
            .subscribeWith(
                object : DisposableObserver<T>() {
                    override fun onNext(item: T) = onNext(item)
                    override fun onComplete() = onComplete()
                    override fun onError(e: Throwable) = onError(e)
                })
            .also { disposables.add(it) }
}