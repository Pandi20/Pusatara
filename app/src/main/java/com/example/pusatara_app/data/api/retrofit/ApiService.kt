package com.example.pusatara_app.data.api.retrofit

import com.example.pusatara_app.data.api.response.DetailMediaResponse
import com.example.pusatara_app.data.api.response.LoginResponse
import com.example.pusatara_app.data.api.response.MediaResponse
import com.example.pusatara_app.data.api.response.RegisterResponse
import com.example.pusatara_app.data.api.response.UploadMediaResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @Multipart
    @POST("posts/create")
    suspend fun uploadMedia(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("userId") userId: RequestBody,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody
    ): UploadMediaResponse

    @GET("posts")
    suspend fun getAllMedia(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): MediaResponse

    @GET("posts/{id}")
    suspend fun getMediaById(
        @Header("Authorization") token: String,
        @Path("id") mediaId: Int
    ): DetailMediaResponse
}