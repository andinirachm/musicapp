package id.lagu.view.fragment

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import id.lagu.R
import id.lagu.interfaces.FindView
import id.lagu.model.TrackItem
import id.lagu.model.TrackListItem
import id.lagu.model.TrackResponse
import id.lagu.presenter.FindPresenter
import id.lagu.view.adapter.TrackAdapter
import kotlinx.android.synthetic.main.fragment_find.*


class FindFragment : BaseFragment(), FindView, SearchView.OnQueryTextListener, SearchView.OnCloseListener, TrackAdapter.OnItemClickListener {
    private var trackAdapter: TrackAdapter? = null
    private val arrTrack = ArrayList<TrackListItem>()
    private var presenter: FindPresenter? = null
    private var progressBar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null
    private var arrTrackRealm: ArrayList<TrackItem>? = null

    companion object {
        fun newInstance(): FindFragment {
            var fragment = FindFragment()
            var args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_view.setOnQueryTextListener(this)
        search_view.setOnCloseListener(this)

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.isNestedScrollingEnabled = false

        trackAdapter = TrackAdapter(arrTrack)
        recyclerView?.adapter = trackAdapter
        trackAdapter?.setOnItemClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_find, container, false)

        progressBar = rootView.findViewById(R.id.progress_bar)
        recyclerView = rootView.findViewById(R.id.recycler_view_track)
        presenter = FindPresenter()
        presenter!!.onAttachView(this)
        presenter!!.getAllWishlist(realm!!)
        return rootView
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onFailure(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

        arrTrack.clear()
        trackAdapter?.notifyDataSetChanged()
    }

    override fun onClose(): Boolean {
        arrTrack.clear()
        trackAdapter?.notifyDataSetChanged()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        hideKeyboard(activity!!)
        presenter?.searchTrack(query!!)
        return true
    }

    override fun showLoading() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        progressBar?.visibility = View.GONE
    }

    override fun onSuccessGetResult(response: TrackResponse) {
        arrTrack.clear()
        var arr = response.message?.body?.trackList
        for (item in arr!!) {
            var wishlisted = presenter?.checkWishlist(realm!!, item?.track?.trackId.toString())
            item.track?.wishlisted = wishlisted

        }
        arrTrack.addAll(response.message?.body?.trackList!!)
        trackAdapter?.notifyDataSetChanged()
    }

    override fun onSuccessAllWishlist(realmResults: ArrayList<TrackItem>) {
        arrTrackRealm?.addAll(realmResults)
    }

    override fun onAddWishlist(boolean: Boolean) {
        Toast.makeText(activity, "Add wishlist " + boolean, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClickListener(item: TrackListItem, position: Int) {
        if (!item.track?.wishlisted!!) {
            val trackItem = TrackItem(item.track?.trackId.toString(),
                    item.track?.trackName.toString(),
                    item.track?.albumCoverart100x100.toString(),
                    item.track?.albumName.toString(),
                    item.track?.artistName.toString())
            var track = item
            track.track?.wishlisted = true
            trackAdapter?.notifyItemChanged(position)
            presenter?.addWishlist(realm!!, trackItem)
        } else {
            var track = item
            track.track?.wishlisted = false
            trackAdapter?.notifyItemChanged(position)
            presenter?.delWishlist(realm!!, item.track.trackId!!.toString())
        }
    }

    override fun onRemoveWishlist(boolean: Boolean) {
        Toast.makeText(activity, "Remove wishlist " + boolean, Toast.LENGTH_SHORT).show()
    }
}

