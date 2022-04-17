package com.example.popularlibraries.utils

import android.os.Handler

class Subscriber<T>(
    private val handler: Handler,
    val callback: (T?) -> Unit
) {
    fun invoke(value: T?) {
        handler.post {
            callback.invoke(value)
        }
    }
}

class Publisher<T> {
    private val subscribers: MutableSet<Subscriber<T?>> = mutableSetOf()
    var value: T? = null
        private set
    private var hasFirstValue = false

    fun subscribe(handler: Handler, callback: (T?) -> Unit) {
        val subscriber = Subscriber(handler, callback)
        subscribers.add(subscriber)
        if (hasFirstValue) {
            subscriber.callback.invoke(value)
        }
        value?.let {
            subscriber.callback(it)
        }
    }


    fun unsubscribeAll() {
        subscribers.clear()
    }

    fun post(value: T) {
        hasFirstValue = true
        this.value = value
        subscribers.forEach { it.callback.invoke(value) }
    }
}


