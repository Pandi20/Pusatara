package com.example.pusatara_app.data.api.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BatikResponse(

	@field:SerializedName("patterns")
	val patterns: List<BatikPatternsItem>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class BatikPatternsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
) : Serializable