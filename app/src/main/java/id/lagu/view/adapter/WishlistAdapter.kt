package id.lagu.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.lagu.R
import id.lagu.model.TrackItem
import kotlin.collections.ArrayList

class WishlistAdapter(val listTrack: ArrayList<TrackItem>?) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTrack!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listTrack!!.get(position)
        holder.setData(item)
        holder.imageBtnWishlist.setOnClickListener(OnItemClick(item, position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageViewTrackThumb = itemView.findViewById<ImageView>(R.id.image_view_track_thumb)
        var textViewTrackTitle = itemView.findViewById<TextView>(R.id.text_view_track_title)
        var textViewTrackArtist = itemView.findViewById<TextView>(R.id.text_view_track_artist)
        var textViewTrackAlbum = itemView.findViewById<TextView>(R.id.text_view_track_album)
        var imageBtnWishlist = itemView.findViewById<ImageButton>(R.id.image_btn_wishlist)

        fun setData(item: TrackItem) {
            val trackName = item.name
            val trackId = item.id
            val concate = "$trackId - $trackName"
            val wishlisted = item.wishlisted

            textViewTrackTitle?.setText(concate)
            textViewTrackArtist?.setText(item.artist)
            textViewTrackAlbum?.setText(item.album)
            if (item.thumb != null || item.thumb!!.length != 0)
                Picasso.get()
                        .load(item.thumb)
                        .into(imageViewTrackThumb)

            imageBtnWishlist.isSelected = wishlisted!!
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClickListener(item: TrackItem, position: Int)
    }

    private inner class OnItemClick(internal var item: TrackItem, var position: Int) : View.OnClickListener {

        override fun onClick(v: View) {
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClickListener(item, position)
            }
        }
    }

}