package id.lagu.interfaces

import id.lagu.model.Event

interface CreateEventRealmView {
    fun onSuccessCreateEvent(event: Event, b: Boolean)
    fun onFailureCreateEvent()
}