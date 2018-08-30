package id.lagu.model

import com.google.gson.annotations.SerializedName

data class Body(

	@field:SerializedName("track_list")
	val trackList: List<TrackListItem>? = null
)