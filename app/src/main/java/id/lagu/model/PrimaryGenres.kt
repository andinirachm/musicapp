package id.lagu.model

import com.google.gson.annotations.SerializedName

data class PrimaryGenres(

	@field:SerializedName("music_genre_list")
	val musicGenreList: List<Any?>? = null
)