package id.lagu.model

import com.google.gson.annotations.SerializedName

data class TrackResponse(

        @field:SerializedName("message")
        val message: Message? = null
)