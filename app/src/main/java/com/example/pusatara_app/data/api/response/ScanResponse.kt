package com.example.pusatara_app.data.api.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("ScanResponse")
	val scanResponse: List<ScanResponseItem>
)

data class ScanResponseItem(

	@field:SerializedName("probability")
	val probability: Any,

	@field:SerializedName("className")
	val className: String
)