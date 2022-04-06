package com.example.popularlibraries


interface MainPresenter {
    fun onAttach(view: MainView)
    fun onLogin(login: String, password: String)
    fun onRegistration()
    fun onForgotPassword()

    class Base(private val model: MainModel) : MainPresenter {

        private var view: MainView = MainView.Empty()

        override fun onAttach(view: MainView) {
            this.view = view
            if (model.load()) view.setSuccessLoad(R.string.serverUploadSuccessful)
            else view.setErrorLoad(R.string.serverAccessError)
        }

        override fun onLogin(login: String, password: String) {
            if (login == "" || password == "") {
                view.setError(R.string.enterLoginAndPassword)
            } else {
                if (login == password) {
                    view.setSuccess(R.string.accessAllowed)
                    view.setOpenImage()
                } else view.setError(R.string.loginAndPasswordIncorrect)
            }
        }

        override fun onRegistration() {

        }

        override fun onForgotPassword() {

        }
    }
}