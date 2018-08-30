package id.lagu.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import id.lagu.R
import id.lagu.interfaces.WishlistView
import id.lagu.model.TrackItem
import id.lagu.presenter.WishlistPresenter
import id.lagu.view.adapter.WishlistAdapter

class WishlistFragment : BaseFragment(), WishlistAdapter.OnItemClickListener, WishlistView {
    private var wishlistAdapter: WishlistAdapter? = null
    private var presenter: WishlistPresenter? = null
    private var progressBar: ProgressBar? = null
    private var recyclerViewWishlist: RecyclerView? = null
    private var wishlistArr = ArrayList<TrackItem>()

    override fun onItemClickListener(item: TrackItem, position: Int) {
        presenter?.delWishlist(realm!!, item.id)
        removeAt(position)
    }

    override fun showLoading() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        progressBar?.visibility = View.GONE
    }

    override fun onRemoveWishlist(boolean: Boolean) {
        Toast.makeText(activity, "Remove wishlist " + boolean, Toast.LENGTH_SHORT).show()
    }

    fun removeAt(position: Int) {
        wishlistArr.removeAt(position)
        wishlistAdapter?.notifyItemRemoved(position)
        wishlistAdapter?.notifyItemRangeChanged(position, wishlistArr.size)
    }

    override fun onSuccessAllWishlist(trackItem: ArrayList<TrackItem>) {
        for (item in trackItem!!) {
            item.wishlisted = true
            wishlistArr.add(item)
        }

        wishlistAdapter?.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): WishlistFragment {
            var fragment = WishlistFragment()
            var args = Bundle()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        recyclerViewWishlist?.layoutManager = linearLayoutManager
        recyclerViewWishlist?.isNestedScrollingEnabled = false

        wishlistAdapter = WishlistAdapter(wishlistArr)
        recyclerViewWishlist!!.adapter = wishlistAdapter
        wishlistAdapter?.setOnItemClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.fragment_wishlist, container, false)
        progressBar = rootView.findViewById(R.id.progress_bar_wishlist)
        recyclerViewWishlist = rootView.findViewById(R.id.recycler_view_wishlist)
        presenter = WishlistPresenter()
        presenter!!.onAttachView(this)
        presenter!!.getAllWishlist(realm!!)
        return rootView
    }
}