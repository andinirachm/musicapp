package id.lagu.model

import com.google.gson.annotations.SerializedName

data class Message(

	@field:SerializedName("header")
	val header: Header? = null,

	@field:SerializedName("body")
	val body: Body? = null
)