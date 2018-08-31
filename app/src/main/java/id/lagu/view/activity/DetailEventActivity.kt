package id.lagu.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.lagu.R
import kotlinx.android.synthetic.main.activity_detail_date.*
import kotlinx.android.synthetic.main.content_detail_event.*

class DetailEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detail Event"

        text_view_event_title.text = intent.getStringExtra("name")
        text_view_event_desc.text = intent.getStringExtra("desc")

        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")
        val dateTime = "$date $time"
        text_view_event_date.text = dateTime
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
