package com.example.pusatara_app.view.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pusatara_app.MainActivity
import com.example.pusatara_app.R
import com.example.pusatara_app.data.di.UserPreferences
import com.example.pusatara_app.databinding.ActivityWelcomeBinding
import com.example.pusatara_app.view.login.LoginActivity
import com.example.pusatara_app.view.profile.ProfilePreferences
import com.example.pusatara_app.view.profile.ProfileViewModel
import com.example.pusatara_app.view.profile.ProfileViewModelFactory
import com.example.pusatara_app.view.profile.dataStore
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

        val preference = ProfilePreferences.getInstance(application.dataStore)
        val preferenceViewModel = ViewModelProvider(this, ProfileViewModelFactory(preference))[ProfileViewModel::class.java]
        preferenceViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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