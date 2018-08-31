package id.lagu.interfaces

import id.lagu.model.Event

interface GetEventRealmView {
    fun onSuccessGetEvent(eventList: ArrayList<Event>)
}