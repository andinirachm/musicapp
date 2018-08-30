package id.lagu.service

import id.lagu.BuildConfig
import id.lagu.model.TrackResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

public interface ApiInterface {

    companion object Factory {
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

    @GET("track.search?")
    abstract fun searchTrack(@Query("apikey") apikey: String,
                                @Query("q_artist") artist: String): Call<TrackResponse>
}