package com.example.popularlibraries

interface MainView {

    fun setSuccess(message: Int)
    fun setError(message: Int)
    fun setSuccessLoad(message: Int)
    fun setErrorLoad(message: Int)
    fun setOpenImage()
    fun showProgress()
    fun hideProgress()

    class Empty() : MainView {
        override fun setSuccess(message: Int) = Unit
        override fun setError(message: Int) = Unit
        override fun setSuccessLoad(message: Int) = Unit
        override fun setErrorLoad(message: Int) = Unit
        override fun setOpenImage() = Unit
        override fun showProgress() = Unit
        override fun hideProgress() = Unit
    }
}