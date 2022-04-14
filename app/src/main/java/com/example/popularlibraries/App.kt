package com.example.popularlibraries

import android.app.Application
import android.content.Context
import com.example.popularlibraries.data.MockLoginApiImpl
import com.example.popularlibraries.domain.LoginApi

class App : Application() {

    val api: LoginApi by lazy { MockLoginApiImpl() }
}
    val Context.app : App
        get() {
            return applicationContext as App
        }

