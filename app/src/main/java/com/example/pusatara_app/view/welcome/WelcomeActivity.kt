package com.example.pusatara_app.view.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pusatara_app.MainActivity
import com.example.pusatara_app.R
import com.example.pusatara_app.data.di.UserPreferences
import com.example.pusatara_app.databinding.ActivityWelcomeBinding
import com.example.pusatara_app.view.login.LoginActivity
import com.example.pusatara_app.view.signup.SignupActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.welcome)

        userPreferences = UserPreferences.getInstance(applicationContext)

        setupAction()

        lifecycleScope.launch {
            val token = userPreferences.getToken().first()
            if (!token.isNullOrBlank()) {
                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                intent.putExtra("TOKEN", token)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signupButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}