package com.example.pusatara_app.data.api.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResponse(

	@field:SerializedName("SearchResponse")
	val searchResponse: List<SearchResponseItem>? = null
)

data class SearchResponseItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
) : Serializable
