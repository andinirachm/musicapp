package id.lagu.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import id.lagu.service.LaguSession

open class BaseActivity : AppCompatActivity() {
    var session: LaguSession? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        session = LaguSession(this)
    }
}