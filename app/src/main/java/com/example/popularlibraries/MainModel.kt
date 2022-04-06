package com.example.popularlibraries


import java.util.*


interface MainModel {

    fun load(): Boolean

    class Base : MainModel {
        private var loadState = 0
        private val random = Random()
        override fun load(): Boolean {
            loadState = random.nextInt(2)
            return if (loadState == 0) {
                println("Загрузка к серверу удалась")
                true
            } else {
                println("Ошибка загрузки к серверу")
                false
            }
        }
    }
}