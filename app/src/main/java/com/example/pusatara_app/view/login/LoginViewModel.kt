package com.example.pusatara_app.view.login

import androidx.lifecycle.ViewModel
import com.example.pusatara_app.data.api.response.LoginResponse
import com.example.pusatara_app.data.di.UserPreferences
import com.example.pusatara_app.data.di.UserRepository

class LoginViewModel(private val pref: UserPreferences) : ViewModel() {
    private val userRepository = UserRepository()

    suspend fun login(username: String, password: String): LoginResponse {
        return userRepository.login(username, password)
    }
}