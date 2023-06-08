package ru.sesh.rxjavaproject.lore

import android.annotation.SuppressLint
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.sesh.rxjavaproject.utils.logging
import java.util.concurrent.TimeUnit

/**
 * Flowable позволяет обрабатывать данные, которые поступают очень часто от источника
 *
 * @see [io.reactivex.rxjava3.exceptions.MissingBackpressureException]
 */
object FlowableHandling {

    private val fastFlowable =
        Flowable.range(0, 50000)
            .onBackpressureBuffer()
            .concatMap {
                Flowable.just(it).delay(100, TimeUnit.MILLISECONDS)
            }

    private val slowFlowable = Flowable.interval(1_000, TimeUnit.MILLISECONDS)

    @SuppressLint("CheckResult")
    fun launchSlowFastFlowable() {
        Flowable.zip(slowFlowable, fastFlowable) { slow, fast ->
            "$slow and $fast"
        }
            .subscribeOn(Schedulers.single())
            .subscribe {
                logging(it)
            }
    }

    @SuppressLint("CheckResult")
    fun launchFlowable() {
        fastFlowable.subscribe {
            logging(it.toString())
        }
    }

    private fun Observable<Int>.toDropStrategy() = toFlowable(BackpressureStrategy.DROP)
    private fun Observable<Int>.toMissingStrategy() = toFlowable(BackpressureStrategy.MISSING)
    private fun Observable<Int>.toLatestStrategy() = toFlowable(BackpressureStrategy.LATEST)
    private fun Observable<Int>.toBufferStrategy() = toFlowable(BackpressureStrategy.BUFFER)
    private fun Observable<Int>.toErrorStrategy() = toFlowable(BackpressureStrategy.ERROR)
}