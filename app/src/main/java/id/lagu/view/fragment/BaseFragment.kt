package id.lagu.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import io.realm.Realm
import io.realm.RealmConfiguration

open class BaseFragment : Fragment() {
    var realm: Realm? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Realm.init(activity)
        val configuration = RealmConfiguration.Builder()
                .name("wishlist.realm").build()
        realm = Realm.getInstance(configuration)
    }
}