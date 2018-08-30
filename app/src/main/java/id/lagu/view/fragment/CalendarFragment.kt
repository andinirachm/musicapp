package id.lagu.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import id.lagu.R
import id.lagu.view.activity.DetailDateActivity


class CalendarFragment : Fragment() {
    var calendarView: CalendarView? = null

    companion object {
        fun newInstance(): CalendarFragment {
            var fragment = CalendarFragment()
            var args = Bundle()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView?.setOnDateChangeListener({ view, year, month, dayOfMonth ->
            startActivity(Intent(activity, DetailDateActivity::class.java))
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_calendar, container, false)
        calendarView = rootView.findViewById(R.id.calendar)
        return rootView
    }
}