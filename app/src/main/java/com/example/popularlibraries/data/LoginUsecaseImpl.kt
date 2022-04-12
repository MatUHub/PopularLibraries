package com.example.popularlibraries.data

import com.example.popularlibraries.domain.LoginApi
import com.example.popularlibraries.domain.LoginUsecase
import android.os.Handler

class LoginUsecaseImpl(
    private val api: LoginApi,
    private val uiHandler: Handler
) : LoginUsecase {
    override fun login(login: String, password: String, callback: (Boolean) -> Unit) {
        Thread{
            val result = api.login(login, password)
            uiHandler.post{
                callback(result)
            }
        }.start()
    }
}