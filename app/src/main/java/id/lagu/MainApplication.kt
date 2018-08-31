package id.lagu

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

    companion object {
        fun getRealmCal(): Realm {
            val configurationCal = RealmConfiguration.Builder()
                    .name("calendar.realm").build()
            return Realm.getInstance(configurationCal)
        }

        fun getRealmWishlist(): Realm {
            val configuration = RealmConfiguration.Builder()
                    .name("wishlist.realm").build()
            return Realm.getInstance(configuration)
        }
    }

}