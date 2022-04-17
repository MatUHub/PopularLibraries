package com.example.popularlibraries.ui

import com.example.popularlibraries.utils.Publisher

interface LoginContract {
    interface ViewModel{
        val shouldShowProgress: Publisher<Boolean>
        val isSuccess: Publisher<Boolean>
        val errorText: Publisher<String?>

        fun onLogin(login: String, password: String)

        fun onCredentialsChanged()
    }
}