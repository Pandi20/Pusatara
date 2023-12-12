package com.example.pusatara_app.data.api.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("ScanResponse")
	val scanResponse: List<ScanResponseItem>
)

data class ScanResponseItem(
	@field:SerializedName("rawProbability")
	val rawProbability: Double,

	@field:SerializedName("probability")
	val probability: Double,

	@field:SerializedName("className")
	val className: String
)