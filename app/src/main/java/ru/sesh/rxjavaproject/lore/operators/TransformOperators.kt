package ru.sesh.rxjavaproject.lore.operators

import io.reactivex.rxjava3.core.Observable
import ru.sesh.rxjavaproject.lore.operators.TransformOperators.distinctOperators


object TransformOperators {

    // Оператор buffer собирает элементы и по мере накопления заданного кол-ва отправляет их дальше одним пакетом
    fun bufferOperators() =
        Observable.range(0, 9).buffer(3)

    // Оператор distinct отсеет дубликаты
    fun distinctOperators() =
        Observable.fromArray(1, 1, 2, 2, 3, 3, 4, 5, 5).distinct()

}