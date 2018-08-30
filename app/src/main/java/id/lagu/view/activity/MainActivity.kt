package id.lagu.view.activity

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import id.lagu.R
import id.lagu.view.fragment.CalendarFragment
import id.lagu.view.fragment.CreateFragment
import id.lagu.view.fragment.FindFragment
import id.lagu.view.fragment.WishlistFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    val mGoogleSignInClient: GoogleSignInClient? = null
    var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.selectedItemId = R.id.action_find
        addFragment(FindFragment.Companion.newInstance())
        bottom_navigation.setOnNavigationItemSelectedListener(this)
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
                true
                addFragment(FindFragment.Companion.newInstance())
            }
            R.id.action_wishlist -> {
                true
                addFragment(WishlistFragment.Companion.newInstance())
            }
            R.id.action_create -> {
                true
                addFragment(CreateFragment.Companion.newInstance())
            }
            R.id.action_calendar -> {
                true
                addFragment(CalendarFragment.Companion.newInstance())
            }
            R.id.action_logout -> {
                showDialogLogout()
                dialog?.show()
                true
            }
            else -> false
        }
        return true
    }

}
