package id.lagu.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import io.realm.Realm
import id.lagu.MainApplication

open class BaseFragment : Fragment() {
    var realm: Realm? = null
    var realmCalendar: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = MainApplication.getRealmWishlist()
        realmCalendar = MainApplication.getRealmCal()
    }
}