package com.example.pusatara_app.data.api.response

import com.google.gson.annotations.SerializedName

data class DetailMediaResponse(

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
	val isPoll: Boolean? = null,

	@field:SerializedName("likesCount")
	val likesCount: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("user")
	val user: User? = null
)
