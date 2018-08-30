package id.lagu.model

import com.google.gson.annotations.SerializedName

data class Header(

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("execute_time")
	val executeTime: Double? = null,

	@field:SerializedName("available")
	val available: Int? = null
)