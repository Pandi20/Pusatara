package com.example.pusatara_app.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pusatara_app.MainActivity
import com.example.pusatara_app.R
import com.example.pusatara_app.data.di.UserPreferences
import com.example.pusatara_app.databinding.ActivityLoginBinding
import com.example.pusatara_app.view.signup.SignupActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userPreferences: UserPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.login)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userPreferences = UserPreferences.getInstance(applicationContext)
        val loginViewModelFactory = LoginViewModelFactory(userPreferences)
        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

        playAnimation()

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val username = binding.edLoginUsername.text.toString()
            val password = binding.edLoginPassword.text.toString()
            val loadingProgressBar = binding.ProgressBarLogin

            loginButton.isEnabled = false

            loadingProgressBar.visibility = View.VISIBLE

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = loginViewModel.login(username, password)

                    withContext(Dispatchers.Main) {
                        loadingProgressBar.visibility = View.GONE
                        loginButton.isEnabled = true

                        if (response.token != null) {
                            val token = response.token
                            if (token != null) {
                                userPreferences.saveToken(token)
                                Log.i("LoginActivity", "Token berhasil disimpan: $token")

                                val userName = response.username
                                val email = response.email
                                if (userName != null && email != null) {
                                    userPreferences.saveUsername(userName)
                                    userPreferences.saveEmail(email)
                                }

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.putExtra("TOKEN", token)
                                startActivity(intent)
                                finish()
                            } else {
                                Log.e("LoginActivity", "Token kosong.")
                            }
                        } else {
                            val errorMessage = response.token ?: "Terjadi kesalahan"
                            showErrorDialog(errorMessage)
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        loadingProgressBar.visibility = View.GONE
                        loginButton.isEnabled = true

                        val errorMessage = when (e) {
                            is HttpException -> {
                                if (e.code() == 401) {
                                    getString(R.string.login_invalid)
                                } else if (e.code() == 404) {
                                    getString(R.string.login_invalid)
                                } else {
                                    "${e.message}"
                                }
                            }
                            else -> "${e.message}"
                        }
                        showErrorDialog(errorMessage)
                        Log.e("LoginActivity", errorMessage)
                    }
                }
            }
        }
    }

    private fun showErrorDialog(errorMessage: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.login_failed))
            .setMessage(errorMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun onSignupTextClick(view: View) {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun playAnimation() {
        val loginMessage =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f)
                .setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(200)
        val dontHaveAccount = ObjectAnimator.ofFloat(binding.tvMessageDonthaveAccount, View.ALPHA, 1f).setDuration(0)
        val toSignup = ObjectAnimator.ofFloat(binding.tvDonthaveAccount, View.ALPHA, 1f).setDuration(0)

        AnimatorSet().apply {
            playSequentially(
                loginMessage,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login,
                dontHaveAccount,
                toSignup
            )
            startDelay = 100
        }.start()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}