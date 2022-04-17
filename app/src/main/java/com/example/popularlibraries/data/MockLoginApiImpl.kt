package com.example.popularlibraries.data

import com.example.popularlibraries.domain.LoginApi

class MockLoginApiImpl : LoginApi {
    override fun login(login: String, password: String): Boolean {
        Thread.sleep(3000)
        return login == password
    }

    override fun registration(login: String, password: String, email: String): Boolean {
        Thread.sleep(3000)
        return login.isNotEmpty()
    }

    override fun logout(): Boolean {
        Thread.sleep(3000)
        return true
    }

    override fun forgotPassword(login: String): Boolean {
        Thread.sleep(3000)
        return false
    }
}