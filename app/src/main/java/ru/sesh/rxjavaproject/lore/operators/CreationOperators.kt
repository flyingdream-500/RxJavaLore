package ru.sesh.rxjavaproject.lore.operators

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object CreationOperators {

    fun fromOperator() = Observable.fromArray(1..10)

    // Последовательность с 0 до 9
    fun rangeOperator() = Observable.range(0, 10)

    // Бесконечный интервал данных начиная с 0
    fun intervalOperator() = Observable.interval(1, TimeUnit.SECONDS)

    // Используем синхронный метод, который надо сделать асинхронным
    fun fromCallableOperator() = Observable.fromCallable {
        TimeUnit.SECONDS.sleep(1)
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    // Создаем собственный источник данных, в котором передаем каждую секунду числа в течение 10 секунд
    fun createOperator() =
        Observable.create { emitter ->
            repeat(10) {
                TimeUnit.SECONDS.sleep(1)
                if (emitter.isDisposed) return@create
                emitter.onNext(it)
            }
            if (emitter.isDisposed) return@create
            emitter.onComplete()
        }

}
