package id.lagu

import id.lagu.model.TrackItem
import id.lagu.presenter.CreateEventPresenter
import id.lagu.presenter.FindPresenter
import io.realm.Realm
import io.realm.RealmConfiguration
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WishlistPresenterTest {
    var testRealm: Realm? = null
    private lateinit var presenter: FindPresenter
    private lateinit var presenterCreate: CreateEventPresenter

    @Before
    fun setUp() {
        presenter = FindPresenter()
        presenterCreate = CreateEventPresenter()
        val testConfig = RealmConfiguration.Builder().inMemory().name("calendar.realm").build()
        testRealm = Realm.getInstance(testConfig)
    }

    @After
    fun tearDown() {
        testRealm?.close()
    }

    private fun createDummyEvent(): ArrayList<TrackItem> {
        val list = ArrayList<TrackItem>()
        list.add(TrackItem("1", "Someone Like You", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Adele_2016.jpg/220px-Adele_2016.jpg",
                "AlbumName", "Adele", true))
        list.add(TrackItem("2", "Someone Like You 2", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Adele_2016.jpg/220px-Adele_2016.jpg",
                "AlbumName", "Adele", true))
        return list
    }

    @Test
    fun getAllWishlist() {
        val expected = createDummyEvent()
        testRealm?.executeTransaction({ realm1 -> realm1.copyToRealm(expected) })
        val actual = presenter.getAllWishlist(testRealm!!)
        Assert.assertEquals(actual.size, 2)
    }

    @Test
    fun checkGetWishlist() {
        val id = "2"
        val expected = createDummyEvent()
        testRealm?.executeTransaction({ realm1 -> realm1.copyToRealm(expected) })
        val actual = presenter.getWishlist(testRealm!!, id)
        Assert.assertTrue(actual.name, true)
    }

    @Test
    fun checkDelWishlist() {
        val id = "1"
        val expected = createDummyEvent()
        testRealm?.executeTransaction({ realm1 -> realm1.copyToRealm(expected) })
        testRealm?.executeTransaction({ realm1 ->
            realm1.where(TrackItem::class.java).equalTo("id", id).findAll()
            realm1.deleteAll()
        })
        val actual = presenter.delWishlist(testRealm!!, id)
        Assert.assertTrue(actual)
    }
}