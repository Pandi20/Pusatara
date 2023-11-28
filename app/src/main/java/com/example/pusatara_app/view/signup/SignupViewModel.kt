package com.example.pusatara_app.view.signup

import androidx.lifecycle.ViewModel
import com.example.pusatara_app.data.api.response.RegisterResponse
import com.example.pusatara_app.data.di.UserRepository

class SignupViewModel : ViewModel() {
    private val userRepository = UserRepository()

    suspend fun register(username: String, email: String, password: String): RegisterResponse {
        return userRepository.register(username, email, password)
    }
}