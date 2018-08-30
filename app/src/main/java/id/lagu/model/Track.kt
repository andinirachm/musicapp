package id.lagu.model

import com.google.gson.annotations.SerializedName

data class Track(

        @field:SerializedName("updated_time")
        val updatedTime: String? = null,

        @field:SerializedName("track_share_url")
        val trackShareUrl: String? = null,

        @field:SerializedName("album_coverart_100x100")
        val albumCoverart100x100: String? = null,

        @field:SerializedName("artist_name")
        val artistName: String? = null,

        @field:SerializedName("album_coverart_800x800")
        val albumCoverart800x800: String? = null,

        @field:SerializedName("track_xboxmusic_id")
        val trackXboxmusicId: String? = null,

        @field:SerializedName("track_isrc")
        val trackIsrc: String? = null,

        @field:SerializedName("album_coverart_500x500")
        val albumCoverart500x500: String? = null,

        @field:SerializedName("num_favourite")
        val numFavourite: Int? = null,

        @field:SerializedName("track_rating")
        val trackRating: Int? = null,

        @field:SerializedName("album_name")
        val albumName: String? = null,

        @field:SerializedName("first_release_date")
        val firstReleaseDate: String? = null,

        @field:SerializedName("album_coverart_350x350")
        val albumCoverart350x350: String? = null,

        @field:SerializedName("has_lyrics_crowd")
        val hasLyricsCrowd: Int? = null,

        @field:SerializedName("track_edit_url")
        val trackEditUrl: String? = null,

        @field:SerializedName("track_name")
        val trackName: String? = null,

        @field:SerializedName("primary_genres")
        val primaryGenres: PrimaryGenres? = null,

        @field:SerializedName("track_name_translation_list")
        val trackNameTranslationList: List<Any?>? = null,

        @field:SerializedName("track_soundcloud_id")
        val trackSoundcloudId: String? = null,

        @field:SerializedName("track_length")
        val trackLength: Int? = null,

        @field:SerializedName("commontrack_id")
        val commontrackId: Int? = null,

        @field:SerializedName("subtitle_id")
        val subtitleId: Int? = null,

        @field:SerializedName("artist_id")
        val artistId: Int? = null,

        @field:SerializedName("artist_mbid")
        val artistMbid: String? = null,

        @field:SerializedName("lyrics_id")
        val lyricsId: Int? = null,

        @field:SerializedName("explicit")
        val explicit: Int? = null,

        @field:SerializedName("commontrack_vanity_id")
        val commontrackVanityId: String? = null,

        @field:SerializedName("track_spotify_id")
        val trackSpotifyId: String? = null,

        @field:SerializedName("secondary_genres")
        val secondaryGenres: SecondaryGenres? = null,

        @field:SerializedName("has_richsync")
        val hasRichsync: Int? = null,

        @field:SerializedName("track_id")
        val trackId: Int? = null,

        @field:SerializedName("instrumental")
        val instrumental: Int? = null,

        @field:SerializedName("restricted")
        val restricted: Int? = null,

        @field:SerializedName("has_subtitles")
        val hasSubtitles: Int? = null,

        @field:SerializedName("album_id")
        val albumId: Int? = null,

        @field:SerializedName("track_mbid")
        val trackMbid: String? = null,

        @field:SerializedName("has_lyrics")
        val hasLyrics: Int? = null,

        var wishlisted: Boolean? = false
)