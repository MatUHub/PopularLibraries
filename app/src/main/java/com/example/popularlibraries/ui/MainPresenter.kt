package com.example.popularlibraries.ui

import android.os.Looper
import java.lang.Thread.sleep
import android.os.Handler
import com.example.popularlibraries.R
import com.example.popularlibraries.domain.LoginApi
import com.example.popularlibraries.domain.LoginUsecase

interface MainPresenter {
    fun onAttach(view: MainView)
    fun onLogin(login: String, password: String)
    fun onRegistration()
    fun onForgotPassword()

    class Base(private val loginUsecase: LoginUsecase) : MainPresenter {

        private var view: MainView = MainView.Empty()
        private val handler = Handler(Looper.getMainLooper())


        override fun onAttach(view: MainView) {
            this.view = view

        }

        override fun onLogin(login: String, password: String) {
            view.showProgress()

                loginUsecase.login(login, password){
                    result ->
                    if (result) {
                        view.setError(R.string.enterLoginAndPassword)
                        view.hideProgress()
                    } else {
                        if (login == password) {
                            view.setSuccess(R.string.accessAllowed)
                            view.setOpenImage()
                        } else {
                            view.setError(R.string.loginAndPasswordIncorrect)
                            view.hideProgress()
                        }
                    }
                }
        }

        override fun onRegistration() {
            view.showProgress()
            Thread {
                sleep(2000)
                handler.post {
                    view.setSuccessLoad(R.string.openFrameRegistration)
                    view.hideProgress()
                }
            }.start()
        }

        override fun onForgotPassword() {
            view.showProgress()
            Thread {
                sleep(2000)
                handler.post {
                    view.setSuccessLoad(R.string.openFrameForgetPassword)
                    view.hideProgress()
                }
            }.start()
        }
    }
}