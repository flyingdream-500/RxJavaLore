package ru.sesh.rxjavaproject.lore.hot_and_cold

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observables.ConnectableObservable
import java.util.concurrent.TimeUnit

// Observable не останавливает работу, когда не остается подписчиков
object HotObservable {

    private val intervalObservable = Observable.interval(1, TimeUnit.SECONDS).take(5)

    // Оператор publish создает объект ConnectableObervable.
    val connectableObservable = intervalObservable.publish()

    // Оператор publish создает объект ConnectableObervable. Но вновь прибывшие подписчики будут получать элементы, которые они пропустили
    val replayConnectableObservable = intervalObservable.replay()

    // Оператор refCount из ConnectableObservable сделает Observable, который будет генерировать элементы только пока есть подписчики.
    // Но надо понимать, что это будет Hot Observable, т.к. он будет раздавать одни и те же данные всем подписчикам, а не стартовать работу заново для каждого из них
    val refCountObservable = intervalObservable.publish().refCount()

    // Он начинает работу при первом подписчике, хранит все элементы и выдает их каждому новому подписчику (даже если он пропустил)
    val cacheObservable = intervalObservable.publish().cache()

    // Метод connect начнет работу Observable, независимо от того, подписан кто-то или нет.
    fun <T> ConnectableObservable<T>.startConnectableObservable() {
        connect()
    }

    // Метод autoConnect позволит указать количество подписавшихся, по достижению которого, метод connect будет вызван автоматически
    fun <T> ConnectableObservable<T>.startAutoconnectObservaable(numberOfObservers: Int = 1) =
        autoConnect(numberOfObservers)

}