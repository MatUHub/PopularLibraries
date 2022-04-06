package com.example.popularlibraries

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import com.example.popularlibraries.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), MainView {

    private val presenter = MainPresenter.Base(MainModel.Base())

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.onAttach(this)

        binding.buttonEnter.setOnClickListener {
            hideKeyboard(this)
            presenter.onLogin(
                binding.loginText.text.toString(),
                binding.passwordText.text.toString()
            )
        }

        binding.buttonRegistration.setOnClickListener {
            hideKeyboard(this)
            presenter.onRegistration()
        }

        binding.buttonForgotPassword.setOnClickListener {
            hideKeyboard(this)
            presenter.onForgotPassword()
        }
    }

    override fun setSuccess(message: Int) {
        setSnack(message)
        binding.buttonEnter.isVisible = false
        binding.buttonRegistration.isVisible = false
        binding.buttonForgotPassword.isVisible = false
        binding.loginText.isVisible = false
        binding.passwordText.isVisible = false
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
        binding.imageAccess.setImageResource(R.drawable.ic_open_24)
    }

    override fun showProgress() {
        binding.buttonEnter.isEnabled = false
        binding.buttonRegistration.isEnabled = false
        binding.buttonForgotPassword.isEnabled = false
    }

    override fun hideProgress() {
        binding.buttonEnter.isEnabled = true
        binding.buttonRegistration.isEnabled = true
        binding.buttonForgotPassword.isEnabled = true
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
}