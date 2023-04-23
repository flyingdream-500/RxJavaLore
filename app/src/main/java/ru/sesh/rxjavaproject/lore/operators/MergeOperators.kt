package ru.sesh.rxjavaproject.lore.operators

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.BiFunction


object MergeOperators {

    // Оператор merge объединит элементы из двух Observable в один Observable
    fun mergeOperator() =
        Observable.fromArray(1, 2, 3)
            .mergeWith(
                Observable.fromArray(4, 5, 6)
            )

    // Оператор zip попарно сопоставит элементы из двух Observable
    fun zipOperator() =
        Observable.fromArray("First", "Second", "Third")
            .zipWith(
                Observable.fromArray(1, 2, 3), BiFunction { t1, t2 ->
                    t1 to t2
                })

}