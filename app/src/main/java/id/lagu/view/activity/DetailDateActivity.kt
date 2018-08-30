package id.lagu.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.lagu.R
import kotlinx.android.synthetic.main.activity_detail_date.*

class DetailDateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_date)

        setSupportActionBar(toolbar);
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        getSupportActionBar()?.setTitle("Detail Calendar")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
