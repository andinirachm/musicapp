package id.lagu.interfaces

import id.lagu.model.TrackItem
import io.realm.Realm
import io.realm.RealmResults

interface RealmInterfaces {
        fun addWishlist(realm: Realm, trackItem: TrackItem): Boolean
        fun delWishlist(realm: Realm, id: String): Boolean
        fun getWishlist(realm: Realm, id: String): Boolean
        fun getAllWishlist(realm: Realm): ArrayList<TrackItem>

}