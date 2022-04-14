package com.example.popularlibraries.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import com.example.popularlibraries.App
import com.example.popularlibraries.R
import com.example.popularlibraries.app
import com.example.popularlibraries.data.LoginUsecaseImpl
import com.example.popularlibraries.databinding.ActivityMainBinding
import com.example.popularlibraries.domain.LoginUsecase
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    private var presenter: MainPresenter.Base? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = restorePresenter()
        presenter?.onAttach(this)

        binding.enterButton.setOnClickListener {
            hideKeyboard(this)
            presenter?.onLogin(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.registrationButton.setOnClickListener {
            hideKeyboard(this)

            presenter?.onRegistration()
        }

        binding.forgotPasswordButton.setOnClickListener {
            hideKeyboard(this)
            presenter?.onForgotPassword()
        }
    }

    override fun setSuccess(message: Int) {
        setSnack(message)
        binding.enterButton.isVisible = false
        binding.registrationButton.isVisible = false
        binding.forgotPasswordButton.isVisible = false
        binding.loginEditText.isVisible = false
        binding.passwordEditText.isVisible = false
    }

    override fun setError(message: Int) {
        setSnack(message)
    }

    override fun setSuccessLoad(message: Int) {
        setSnack(message)

    }

    override fun setErrorLoad(message: Int) {
        setSnack(message)
    }

    override fun setOpenImage() {
        binding.accessImageView.setImageResource(R.drawable.ic_open_24)
    }

    override fun showProgress() {
        binding.enterButton.isEnabled = false
        binding.registrationButton.isEnabled = false
        binding.forgotPasswordButton.isEnabled = false
    }

    override fun hideProgress() {
        binding.enterButton.isEnabled = true
        binding.registrationButton.isEnabled = true
        binding.forgotPasswordButton.isEnabled = true
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setSnack(message: Int) {
        Snackbar.make(binding.container, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun restorePresenter(): MainPresenter.Base {
        val presenter = lastCustomNonConfigurationInstance as? MainPresenter.Base
        val usecase: LoginUsecase = LoginUsecaseImpl(app.api, Handler(Looper.getMainLooper()))
        return presenter ?: MainPresenter.Base(usecase)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }
}