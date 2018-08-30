package id.lagu.interfaces

import id.lagu.model.TrackItem
import id.lagu.model.TrackResponse

interface WishlistView {
    fun showLoading()
    fun dismissLoading()
    fun onRemoveWishlist(boolean: Boolean)
    fun onSuccessAllWishlist(trackItem: ArrayList<TrackItem>)
}