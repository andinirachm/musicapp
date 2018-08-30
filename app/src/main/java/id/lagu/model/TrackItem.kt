package id.lagu.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class TrackItem(
        @PrimaryKey open var id: String = "",
        open var name: String = "",
        open var thumb: String = "",
        open var album: String = "",
        open var artist: String = ""
) : RealmObject() {

    fun copy(
            id: String = this.id,
            name: String = this.name,
            thumb: String = this.thumb,
            album: String = this.album,
            artist: String = this.artist) = TrackItem(id, name, thumb, album, artist)
}