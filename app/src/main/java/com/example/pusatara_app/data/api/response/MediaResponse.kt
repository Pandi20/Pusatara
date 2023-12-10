package com.example.pusatara_app.data.api.response

import com.google.gson.annotations.SerializedName

data class MediaResponse(

	@field:SerializedName("data")
	val data: List<DataItem> = emptyList(),

	@field:SerializedName("totalPages")
	val totalPages: Int? = null,

	@field:SerializedName("currentPage")
	val currentPage: Int? = null
)

data class DataItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("isPoll")
	var isPoll: Boolean? = null,

	@field:SerializedName("likesCount")
	val likesCount: Int? = null,

	@field:SerializedName("isLiked")
	var isLiked: Int? = null,

	@field:SerializedName("user")
	val user: User? = null
)

data class User(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("username")
	val username: String? = null
)
