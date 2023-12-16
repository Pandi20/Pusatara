package com.example.pusatara_app.data.api.response

import com.google.gson.annotations.SerializedName

data class GlossaryResponse(

	@field:SerializedName("GlossaryResponse")
	val glossaryResponse: List<GlossaryResponseItem?>? = null
)

data class GlossaryResponseItem(

	@field:SerializedName("types")
	val types: List<TypesItem?>? = null,

	@field:SerializedName("patterns")
	val patterns: List<PatternsItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class PatternsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)

data class TypesItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)
