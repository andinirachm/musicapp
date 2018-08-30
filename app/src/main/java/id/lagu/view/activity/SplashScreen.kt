package id.lagu.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.lagu.R

class SplashScreen : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        showSplash()
    }

    fun showSplash() {
        val timer = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep((5 * 1000).toLong())
                    finish()
                    if (session!!.isAuthenticated()!!) {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        startActivity(Intent(applicationContext, LoginActivity::class.java))
                    }
                } catch (e: Exception) {

                }

            }
        }
        timer.start()
    }
}
