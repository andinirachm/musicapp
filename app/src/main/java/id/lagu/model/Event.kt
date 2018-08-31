package id.lagu.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Event(
        @PrimaryKey open var id: Int = 0,
        open var name: String = "",
        open var date: String = "",
        open var time: String = "",
        open var desc: String = ""
) : RealmObject() {

    fun copy(
            id: Int = this.id,
            name: String = this.name,
            date: String = this.date,
            time: String = this.time,
            desc: String = this.desc) = Event(id, name, date, time, desc)

}