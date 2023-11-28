package com.example.pusatara_app.data.di

import com.example.pusatara_app.data.api.response.LoginResponse
import com.example.pusatara_app.data.api.response.RegisterResponse
import com.example.pusatara_app.data.api.retrofit.ApiConfig
import com.example.pusatara_app.data.api.retrofit.ApiService

class UserRepository {
    private val apiService: ApiService = ApiConfig.getApiService()

    suspend fun register(username: String, email: String, password: String): RegisterResponse {
        return apiService.register(username, email, password)
    }

    suspend fun login(username: String, password: String): LoginResponse {
        return apiService.login(username, password)
    }
}