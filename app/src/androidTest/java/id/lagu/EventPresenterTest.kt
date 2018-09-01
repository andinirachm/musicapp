package id.lagu

import org.junit.Before
import org.junit.Test

import io.realm.RealmConfiguration
import org.junit.After
import id.lagu.model.Event
import id.lagu.presenter.CreateEventPresenter
import id.lagu.presenter.GetEventPresenter
import io.realm.Realm
import org.junit.Assert

class EventPresenterTest {
    var testRealm: Realm? = null
    private lateinit var presenter: GetEventPresenter
    private lateinit var presenterCreate: CreateEventPresenter

    @Before
    fun setUp() {
        presenter = GetEventPresenter()
        presenterCreate = CreateEventPresenter()
        val testConfig = RealmConfiguration.Builder().inMemory().name("calendar.realm").build()
        testRealm = Realm.getInstance(testConfig)
    }

    @After
    fun tearDown() {
        testRealm?.close()
    }

    private fun createDummyEvent(): ArrayList<Event> {
        val list = ArrayList<Event>()
        list.add(Event(1, "Weekly Meeting", "01/09/18", "12:00", "Meeting with all member"))
        list.add(Event(2, "Weekly Meeting 2", "02/09/18", "12:00", "Meeting with all member"))
        list.add(Event(3, "Weekly Meeting 3", "01/09/18", "12:00", "Meeting with all member"))
        return list
    }

    @Test
    fun checkEventListSizeByDate() {
        val expected = createDummyEvent()
        val list = ArrayList<Event>()
        val date = "02/09/18"
        testRealm?.executeTransaction({ realm1 -> realm1.copyToRealm(expected) })

        for (item in expected) {
            if (item.date == date)
                list.add(item)
        }

        val actual = presenter.getEventByDate(testRealm!!, date)
        Assert.assertSame(actual.size, list.size)
    }
}