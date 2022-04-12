package com.example.popularlibraries.domain

import javax.security.auth.callback.Callback

interface LoginUsecase {
    fun login(login: String, password: String, callback: (Boolean) -> Unit)
}