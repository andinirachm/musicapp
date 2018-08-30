package id.lagu.presenter

import android.util.Log
import id.lagu.BuildConfig
import id.lagu.interfaces.WishlistView
import id.lagu.interfaces.RealmInterfaces
import id.lagu.interfaces.WishlistRealmInterfaces
import id.lagu.model.TrackItem
import id.lagu.model.TrackResponse
import id.lagu.service.ApiInterface
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import io.realm.RealmResults


class WishlistPresenter : BasePresenter<WishlistView>, WishlistRealmInterfaces {
    private var view: WishlistView? = null

    override fun onAttachView(view: WishlistView?) {
        this.view = view
    }

    override fun onDetachView() {
        this.view = null
    }

    override fun delWishlist(realm: Realm, id: String): Boolean {
        try {
            realm.beginTransaction()
            val item = realm.where(TrackItem::class.java).equalTo("id", id).findFirst()
            if (item != null) {
                if (!realm.isInTransaction()) {
                    realm.beginTransaction();
                }
                item.deleteFromRealm();
                realm.commitTransaction();
            }
            view?.onRemoveWishlist(true)
            return true
        } catch (e: Exception) {
            println(e)
            view?.onRemoveWishlist(false)
            return false
        }
    }

    override fun getAllWishlist(realm: Realm): ArrayList<TrackItem> {
        val list = ArrayList<TrackItem>()
        val results = realm.where(TrackItem::class.java).findAll()
        list.addAll(realm.copyFromRealm(results));
        view?.onSuccessAllWishlist(list)
        return list
    }

    override fun getWishlist(realm: Realm, id: String): Boolean {
        val trackItem = realm.where(TrackItem::class.java).equalTo("id", id).findFirst()
        return trackItem != null
    }
}
