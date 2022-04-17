package com.example.popularlibraries.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import com.example.popularlibraries.R
import com.example.popularlibraries.app
import com.example.popularlibraries.data.LoginUsecaseImpl
import com.example.popularlibraries.databinding.ActivityMainBinding
import com.example.popularlibraries.domain.LoginUsecase
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var viewModel: LoginViewModel.Base? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = restoreViewModel()


        binding.enterButton.setOnClickListener {
            hideKeyboard(this)
            viewModel?.onLogin(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.registrationButton.setOnClickListener {
            hideKeyboard(this)


        }

        binding.forgotPasswordButton.setOnClickListener {
            hideKeyboard(this)

        }

        viewModel?.shouldShowProgress?.subscribe { shouldShow ->
            if (shouldShow == true) {
                showProgress()
            } else {
                hideProgress()
            }
        }



        viewModel?.isSuccess?.subscribe {
            if (it == true) {
                setSuccess()
            }
        }

        viewModel?.errorText?.subscribe {
            it?.let {
                val success = viewModel?.isSuccess?.value
                if (success == false) {
                    setError(it)
                }
            }
        }
    }

    private fun setSuccess() {
        binding.enterButton.isVisible = false
        binding.registrationButton.isVisible = false
        binding.forgotPasswordButton.isVisible = false
        binding.loginEditText.isVisible = false
        binding.passwordEditText.isVisible = false
    }

    fun setError(message: String) {
        setSnack(message)
    }

    fun setSuccessLoad(message: String) {
        setSnack(message)

    }

    fun setErrorLoad(message: String) {
        setSnack(message)
    }

    fun setOpenImage() {
        binding.accessImageView.setImageResource(R.drawable.ic_open_24)
    }

    fun showProgress() {
        binding.enterButton.isEnabled = false
        binding.registrationButton.isEnabled = false
        binding.forgotPasswordButton.isEnabled = false
    }

    fun hideProgress() {
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

    private fun setSnack(message: String) {
        Snackbar.make(binding.container, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun restoreViewModel(): LoginViewModel.Base {
        val presenter = lastCustomNonConfigurationInstance as? LoginViewModel.Base
        val usecase: LoginUsecase = LoginUsecaseImpl(app.api, Handler(Looper.getMainLooper()))
        return presenter ?: LoginViewModel.Base(usecase)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.isSuccess?.unsubscribeAll()
        viewModel?.errorText?.unsubscribeAll()
        viewModel?.shouldShowProgress?.unsubscribeAll()
    }
}