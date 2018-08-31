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
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {
    private var calendarView: CalendarView? = null
    private val myFormatDate = "dd/MM/yy"

    companion object {
        fun newInstance(): CalendarFragment {
            val fragment = CalendarFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView?.setOnDateChangeListener({ _, year, month, dayOfMonth ->

            val myCalendar = Calendar.getInstance()
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val sdf = SimpleDateFormat(myFormatDate, Locale.getDefault())
            val intent = Intent(activity, DetailDateActivity::class.java)
            intent.putExtra("date", sdf.format(myCalendar.time))
            startActivity(intent)
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_calendar, container, false)
        calendarView = rootView.findViewById(R.id.calendar)
        return rootView
    }
}