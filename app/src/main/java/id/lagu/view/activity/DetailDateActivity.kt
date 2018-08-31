package id.lagu.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import id.lagu.MainApplication
import id.lagu.R
import id.lagu.interfaces.GetEventRealmView
import id.lagu.model.Event
import id.lagu.presenter.GetEventPresenter
import id.lagu.view.adapter.EventAdapter
import kotlinx.android.synthetic.main.activity_detail_date.*

class DetailDateActivity : BaseActivity(), GetEventRealmView, EventAdapter.OnItemClickListener {
    override fun onItemClickListener(item: Event, position: Int) {
        val intent = Intent(this, DetailEventActivity::class.java)
        intent.putExtra("name", item.name)
        intent.putExtra("desc", item.desc)
        intent.putExtra("date", item.date)
        intent.putExtra("time", item.time)
        startActivity(intent)
    }

    private var presenter: GetEventPresenter? = null
    private var arrEvent = ArrayList<Event>()
    private var recyclerView: RecyclerView? = null
    private var adapter: EventAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_date)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detail Date"

        recyclerView = findViewById(R.id.recycler_view_event)
        presenter = GetEventPresenter()
        presenter?.onAttachView(this)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.isNestedScrollingEnabled = false
        adapter = EventAdapter(arrEvent)
        recyclerView!!.adapter = adapter
        adapter?.setOnItemClickListener(this)
        presenter?.getEventByDate(MainApplication.getRealmCal(), intent.getStringExtra("date"))
    }

    override fun onSuccessGetEvent(eventList: ArrayList<Event>) {
        arrEvent.clear()
        arrEvent.addAll(eventList)
        adapter?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDetachView()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
