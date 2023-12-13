package com.example.pusatara_app.data.api.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("className")
	val className: String,

	@field:SerializedName("probability")
	val probability: Double,

	@field:SerializedName("rawProbability")
	val rawProbability: Double

)