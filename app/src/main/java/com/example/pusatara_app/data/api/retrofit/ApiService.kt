package com.example.pusatara_app.data.api.retrofit

import com.example.pusatara_app.data.api.response.LoginResponse
import com.example.pusatara_app.data.api.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("auth/signup")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("role") role: String = "user"
    ): RegisterResponse

    @FormUrlEncoded
    @POST("auth/signin")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse
}