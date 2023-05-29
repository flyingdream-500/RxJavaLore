package ru.sesh.rxjavaproject.lore.error

import android.annotation.SuppressLint
import io.reactivex.rxjava3.core.Observable
import ru.sesh.rxjavaproject.utils.logging
import java.util.concurrent.TimeUnit
import java.util.function.BiPredicate

object ErrorHandling {

    private val errorObservable = Observable.just("1", "2", "q", "4")
        .map(Integer::parseInt)

    @SuppressLint("CheckResult")
    private fun Observable<Int>.subscribing() {
        subscribe(
            {
                logging(it.toString())
            }, {
                logging(it.message.orEmpty())
            }
        )
    }

    fun onErrorExample() {
        errorObservable.subscribing()
    }

    /**
     * onErrorReturn ловит ошибку и передает какое-то значение в onNext,
     * в итоге это все равно приводит к тому, что поток данных завершается (onCompleted)
     */
    fun onErrorReturnExample() {
        errorObservable
            .onErrorReturn { -1 }
            .subscribing()
    }

    /**
     * Получаем значения, а после ошибки - значения из последовательности, указанной в onErrorResumeNext
     */
    fun onErrorResumeNextExample() {
        errorObservable
            .onErrorResumeNext { Observable.just(10, 11, 13) }
            .subscribing()
    }

    /**
     * Оператор retry пытается повторно получить данные после ошибки
     */
    fun retryExample() {
        errorObservable
            .retry(3)
            .subscribing()
    }

    /**
     * throwableObservable - это Observable, куда будут приходить ошибки из errorObservable.
     * От нас требуется вернуть, как результат работы функции, Observable, который будет использован, как триггер перезапуска метода errorObservable
     * Временной интервал мы указали в операторе delay, а количество попыток - в take
     */
    fun retryWhenExample() {
        errorObservable
            .retryWhen { throwableObservable ->
                throwableObservable.take(3).delay(1, TimeUnit.SECONDS)
            }
            .subscribing()
    }

}