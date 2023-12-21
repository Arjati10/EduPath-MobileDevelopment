package com.example.edupath.data

import com.google.gson.annotations.SerializedName

data class CommunityResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CommunitiesItem(

	@field:SerializedName("members")
	val members: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("status")
	val status: String? = null

//	@field:SerializedName("image")
//	val image: String? = null
)


data class Data(

	@field:SerializedName("communities")
	val communities: List<CommunitiesItem?>? = null
)
