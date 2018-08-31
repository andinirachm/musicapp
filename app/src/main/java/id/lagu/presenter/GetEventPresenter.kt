package id.lagu.presenter

import id.lagu.interfaces.GetEventRealmView
import id.lagu.model.Event
import io.realm.Realm


class GetEventPresenter : BasePresenter<GetEventRealmView>{
    private var view: GetEventRealmView? = null

    override fun onAttachView(view: GetEventRealmView?) {
        this.view = view
    }

    override fun onDetachView() {
        this.view = null
    }

    fun getEventByDate(realm: Realm, date: String): ArrayList<Event> {
        val list = ArrayList<Event>()
        val results = realm.where(Event::class.java).equalTo("date", date).findAll()
        list.addAll(realm.copyFromRealm(results))
        view?.onSuccessGetEvent(list)
        return list
    }
}
