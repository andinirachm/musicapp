package id.lagu.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import id.lagu.R
import id.lagu.view.fragment.CalendarFragment
import id.lagu.view.fragment.CreateFragment
import id.lagu.view.fragment.FindFragment
import id.lagu.view.fragment.WishlistFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var dialog: AlertDialog? = null
    private var gso: GoogleSignInOptions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        toolbar.title = "Find"

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, this.gso!!)

        bottom_navigation.selectedItemId = R.id.action_find
        addFragment(FindFragment.Companion.newInstance())
        bottom_navigation.setOnNavigationItemSelectedListener(this)

        val extras = intent.extras

        if (extras != null) {
            if (extras.getString("name") != null) {
                val name = extras.getString("name")
                val id = extras.getString("id")
                val desc = extras.getString("desc")
                val date = extras.getString("date")
                val time = extras.getString("time")

                bottom_navigation.selectedItemId = R.id.action_calendar
                addFragment(CalendarFragment.Companion.newInstance())
                Log.e("INTENT", name + " - " + id)
                val intent = Intent(this, DetailEventActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("desc", desc)
                intent.putExtra("date", date)
                intent.putExtra("time", time)
                startActivity(intent)
            } else {
                Log.e("INTENT", "KOSONG")
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.frame_layout, fragment, fragment.javaClass.getSimpleName())
                .commit()
    }

    private fun showDialogLogout() {
        val li = LayoutInflater.from(this)
        val dialogView = li.inflate(R.layout.layout_logout, null)

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setView(dialogView)

        alertDialog.setCancelable(true)
        alertDialog.setPositiveButton("Yes") { dialog, which ->
            if (session?.isLoginViaGoogle()!!) {
                mGoogleSignInClient?.signOut()?.addOnCompleteListener(this, OnCompleteListener<Void> { })
            } else if (session?.isLoginViaFacebook()!!) {
                LoginManager.getInstance().logOut()
            }
            session?.clear()
            session?.logout()
            dialog.dismiss()
            Toast.makeText(this, "Logout success", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SplashScreen::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        alertDialog.setNegativeButton("No") { dialog, which -> dialog.dismiss() }
        dialog = alertDialog.create()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_find -> {
                toolbar.title = "Find"
                addFragment(FindFragment.Companion.newInstance())
            }
            R.id.action_wishlist -> {
                toolbar.title = "Wishlist"
                addFragment(WishlistFragment.Companion.newInstance())
            }
            R.id.action_create -> {
                toolbar.title = "Create Event"
                addFragment(CreateFragment.Companion.newInstance())
            }
            R.id.action_calendar -> {
                toolbar.title = "Event"
                addFragment(CalendarFragment.Companion.newInstance())
            }
            R.id.action_logout -> {
                showDialogLogout()
                dialog?.show()
            }
            else -> false
        }
        return true
    }
}
