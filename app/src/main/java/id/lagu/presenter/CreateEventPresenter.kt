package id.lagu.presenter

import id.lagu.interfaces.CreateEventRealmView
import id.lagu.model.Event
import io.realm.Realm


class CreateEventPresenter : BasePresenter<CreateEventRealmView> {
    private var view: CreateEventRealmView? = null

    override fun onAttachView(view: CreateEventRealmView?) {
        this.view = view
    }

    override fun onDetachView() {
        this.view = null
    }

    fun addEvent(realm: Realm, event: Event): Boolean {
        try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(event)
            realm.commitTransaction()
            view?.onSuccessCreateEvent(event, true)
            return true
        } catch (e: Exception) {
            println(e)
            view?.onFailureCreateEvent()
            return false
        }
    }

}
