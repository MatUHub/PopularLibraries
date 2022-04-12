package com.example.popularlibraries.domain

interface LoginApi {
    fun login(login: String, password: String): Boolean
    fun registration(login: String, password: String, email: String): Boolean
    fun logout(): Boolean
    fun forgotPassword(login: String): Boolean
}