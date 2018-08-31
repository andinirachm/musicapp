package id.lagu.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.lagu.R
import id.lagu.model.Event

class EventAdapter(val listEvent: ArrayList<Event>?) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listEvent!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listEvent!!.get(position)
        holder.setData(item)
        holder.itemView.setOnClickListener(OnItemClick(item, position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewEventTitle = itemView.findViewById<TextView>(R.id.text_view_event_title)
        var textViewEventDesc = itemView.findViewById<TextView>(R.id.text_view_event_desc)
        var textViewEventDate = itemView.findViewById<TextView>(R.id.text_view_event_date)

        fun setData(item: Event) {
            textViewEventTitle.text = item.name
            textViewEventDesc.text = item.desc
            val dateTime = "${item.date} ${item.time}"
            textViewEventDate.text = dateTime
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClickListener(item: Event, position: Int)
    }

    private inner class OnItemClick(internal var item: Event, var position: Int) : View.OnClickListener {

        override fun onClick(v: View) {
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClickListener(item, position)
            }
        }
    }

}