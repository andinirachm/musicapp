package id.lagu.model

import com.google.gson.annotations.SerializedName

data class TrackListItem(

	@field:SerializedName("track")
	val track: Track? = null
)