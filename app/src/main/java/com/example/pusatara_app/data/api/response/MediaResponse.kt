package com.example.pusatara_app.data.api.response

import com.google.gson.annotations.SerializedName

data class MediaResponse(

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("postId")
	val postId: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)
