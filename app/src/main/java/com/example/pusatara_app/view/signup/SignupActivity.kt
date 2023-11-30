package com.example.pusatara_app.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.pusatara_app.R
import com.example.pusatara_app.databinding.ActivitySignupBinding
import com.example.pusatara_app.view.login.LoginActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Suppress("DEPRECATION")
class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var signupViewModel: SignupViewModel
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.signup)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        signupViewModel = ViewModelProvider(this)[SignupViewModel::class.java]

        playAnimation()

        val signupButton = binding.signupButton
        signupButton.setOnClickListener {
            val name = binding.edRegisterUsername.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showErrorDialog(getString(R.string.empty_fields_error))
                return@setOnClickListener
            }

            val loadingProgressBar = binding.ProgressBarSignup

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    runOnUiThread {
                        loadingProgressBar.visibility = View.VISIBLE
                    }

                    val response = signupViewModel.register(name, email, password)
                    runOnUiThread {
                        loadingProgressBar.visibility = View.GONE

                        if (response.message == "User registered successfully!") {
                            val message = response.message
                            showSuccessDialog(message)
                            Log.d("SignupActivity", "Pendaftaran berhasil: $message")
                        } else {
                            val errorMessage = response.message ?: "Terjadi kesalahan"
                            showErrorDialog(errorMessage)
                            Log.e("SignupActivity", "Pendaftaran gagal: $errorMessage")
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        loadingProgressBar.visibility = View.GONE

                        val errorMessage = when (e) {
                            is HttpException -> {
                                if (e.code() == 400) {
                                    getString(R.string.reg_dupplicate)
                                } else {
                                    "${e.message}"
                                }
                            }
                            else -> "${e.message}"
                        }
                        showErrorDialog(errorMessage)
                        Log.e("SignupActivity", errorMessage)
                    }
                }
            }
        }
    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.reg_success))
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()

                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            .show()
    }


    private fun showErrorDialog(errorMessage: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.reg_failed))
            .setMessage(errorMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun onLoginTextClick(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)
        val alreadyAccount = ObjectAnimator.ofFloat(binding.tvMessageAlreadyAccount, View.ALPHA, 1f).setDuration(0)
        val toLogin = ObjectAnimator.ofFloat(binding.tvAlreadyAccount, View.ALPHA, 1f).setDuration(0)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup,
                alreadyAccount,
                toLogin
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