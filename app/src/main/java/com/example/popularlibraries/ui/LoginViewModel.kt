package com.example.popularlibraries.ui

import android.os.Looper
import java.lang.Thread.sleep
import android.os.Handler
import com.example.popularlibraries.R
import com.example.popularlibraries.domain.LoginUsecase
import com.example.popularlibraries.utils.Publisher

interface LoginViewModel {

    interface ViewModel {
        val shouldShowProgress: Publisher<Boolean>
        val isSuccess: Publisher<Boolean>
        val errorText: Publisher<String?>

        fun onLogin(login: String, password: String)

    }

    class Base(private val loginUsecase: LoginUsecase) : ViewModel {

        override val shouldShowProgress: Publisher<Boolean> = Publisher()
        override val isSuccess: Publisher<Boolean> = Publisher()
        override val errorText: Publisher<String?> = Publisher()


        override fun onLogin(login: String, password: String) {
            shouldShowProgress.post(true)
            loginUsecase.login(login, password) { result ->
                shouldShowProgress.post(false)
                if (result) {
                    errorText.post("")
                    isSuccess.post(true)
                    shouldShowProgress.post(false)
                } else {
                    if (login == password) {
                        errorText.post("Доступ разрешен")
                        isSuccess.post(false)
                        shouldShowProgress.post(false)
                    } else {
                        errorText.post("Логин или пароль не верны")
                        isSuccess.post(false)
                        shouldShowProgress.post(false)
                    }
                }
            }
        }

        /*//override fun onRegistration() {
            view.showProgress()
            Thread {
                sleep(2000)
                handler.post {
                    view.setSuccessLoad(R.string.openFrameRegistration)
                    view.hideProgress()
                }
            }.start()
        }*/

        /*override fun onForgotPassword() {
            view.showProgress()
            Thread {
                sleep(2000)
                handler.post {
                    view.setSuccessLoad(R.string.openFrameForgetPassword)
                    view.hideProgress()
                }
            }.start()
        }*/
    }
}