package id.lagu.interfaces

import id.lagu.model.TrackItem
import id.lagu.model.TrackResponse

interface FindView {
    fun showLoading()
    fun dismissLoading()
    fun onSuccessGetResult(response: TrackResponse)
    fun onFailure(message: String?)
    fun onAddWishlist(boolean: Boolean)
    fun onRemoveWishlist(boolean: Boolean)
    fun onSuccessAllWishlist(trackItem: ArrayList<TrackItem>)
}