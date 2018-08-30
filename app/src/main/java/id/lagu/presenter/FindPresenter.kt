package id.lagu.presenter

import android.util.Log
import id.lagu.BuildConfig
import id.lagu.interfaces.FindView
import id.lagu.interfaces.RealmInterfaces
import id.lagu.model.TrackItem
import id.lagu.model.TrackResponse
import id.lagu.service.ApiInterface
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import io.realm.RealmResults


class FindPresenter : BasePresenter<FindView>, RealmInterfaces {
    private var view: FindView? = null

    override fun onAttachView(view: FindView?) {
        this.view = view
    }

    override fun onDetachView() {
        this.view = null
    }

    fun searchTrack(artist: String) {
        view?.showLoading()
        val apiService = ApiInterface.create()
        val call = apiService.searchTrack(BuildConfig.APIKEY, artist)
        call.enqueue(object : Callback<TrackResponse> {
            override fun onResponse(call: Call<TrackResponse>, response: Response<TrackResponse>) {
                view?.dismissLoading()
                if (response.isSuccessful) {
                    view?.onSuccessGetResult(response.body()!!)
                } else {

                    view?.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                view?.dismissLoading()
                view?.onFailure(t.message)
            }
        })

    }

    override fun addWishlist(realm: Realm, trackItem: TrackItem): Boolean {
        try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(trackItem)
            realm.commitTransaction()
            view?.onAddWishlist(true)
            return true
        } catch (e: Exception) {
            println(e)
            view?.onAddWishlist(false)
            return false
        }
    }

    override fun delWishlist(realm: Realm, id: String): Boolean {
        try {
            realm.beginTransaction()
            realm.where(TrackItem::class.java).equalTo("id", id).findAll()
            realm.deleteAll()
            realm.commitTransaction()
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
