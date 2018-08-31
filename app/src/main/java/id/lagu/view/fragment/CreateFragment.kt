package id.lagu.view.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import id.lagu.R
import id.lagu.interfaces.CreateEventRealmView
import id.lagu.model.Event
import id.lagu.presenter.CreateEventPresenter
import java.text.SimpleDateFormat
import java.util.*

class CreateFragment : BaseFragment(), CreateEventRealmView {
    private var btnPickTime: Button? = null
    private var btnPickDate: Button? = null
    private var editTextTime: EditText? = null
    private var editTextDate: EditText? = null
    private var editTextTitle: EditText? = null
    private var editTextDesc: EditText? = null
    private var btnCreate: Button? = null
    private val myFormatDate = "dd/MM/yy"
    private val formatDate = "dd/MM/yy HH:mm"
    private val myFormatTime = "HH:mm"
    private var presenter: CreateEventPresenter? = null
    private var daySelected = 0
    private var monthSelected = 0
    private var yearSelected = 0

    companion object {
        fun newInstance(): CreateFragment {
            val fragment = CreateFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_create, container, false)
        btnPickDate = rootView.findViewById(R.id.btn_pick_date)
        btnPickTime = rootView.findViewById(R.id.btn_pick_time)
        btnCreate = rootView.findViewById(R.id.btn_create_event)
        editTextDate = rootView.findViewById(R.id.edit_text_event_date)
        editTextTime = rootView.findViewById(R.id.edit_text_event_time)
        editTextTitle = rootView.findViewById(R.id.edit_text_event_title)
        editTextDesc = rootView.findViewById(R.id.edit_text_event_desc)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = CreateEventPresenter()
        presenter?.onAttachView(this)

        btnPickDate?.setOnClickListener {
            showDatePickerDialog()
        }

        btnPickTime?.setOnClickListener {
            showTimePickerDialog()
        }

        btnCreate?.setOnClickListener {
            if (editTextTitle?.text.toString().equals("") || editTextDesc?.text.toString().equals("")
                    || editTextDate?.text.toString().equals("") || editTextTitle?.text.toString().equals("")) {
                Toast.makeText(activity, "Please fill the form.", Toast.LENGTH_SHORT).show()
            } else {
                realm!!.beginTransaction()
                val maxValue = realmCalendar!!.where(Event::class.java).max("id")
                val id: Int
                if (maxValue != null) {
                    id = maxValue.toInt() + 1
                } else {
                    id = 0
                }

                realm!!.commitTransaction()
                val event = Event(id, editTextTitle?.text.toString(), editTextDate?.text.toString(), editTextTime?.text.toString(), editTextDesc?.text.toString())
                presenter?.addEvent(realmCalendar!!, event)
            }
        }
    }

    private fun showTimePickerDialog() {
        val mcurrentTime = Calendar.getInstance()
        val mHour = mcurrentTime.get(Calendar.HOUR)
        val mMinute = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker = TimePickerDialog(activity!!, TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            val myTime = Calendar.getInstance()
            myTime.set(Calendar.HOUR, hour)
            myTime.set(Calendar.MINUTE, minute)

            val sdf = SimpleDateFormat(myFormatTime, Locale.getDefault())
            val fullSdf = SimpleDateFormat(formatDate, Locale.getDefault())
            val month = monthSelected + 1
            val fullDateTimeStr = "$daySelected/$month/$yearSelected $hour:$minute"
            val date = fullSdf.parse(fullDateTimeStr)
            val time = sdf.parse("$hour:$minute")
            if (date.after(mcurrentTime.time)) {
                editTextTime?.setText(sdf.format(time))
            } else
                Toast.makeText(activity, "Date time selected couldn't less than current, Please select right time.", Toast.LENGTH_LONG).show()
        }, mHour, mMinute, true)

        if (editTextDate?.text.toString().equals(""))
            Toast.makeText(activity, "Please pick date first.", Toast.LENGTH_SHORT).show()
        else
            mTimePicker.show()
    }

    private fun showDatePickerDialog() {
        val mcurrentDate = Calendar.getInstance()
        val mYear = mcurrentDate.get(Calendar.YEAR)
        val mMonth = mcurrentDate.get(Calendar.MONTH)
        val mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH)

        val mDatePicker = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { datepicker, selectedyear, selectedmonth, selectedday ->
            val myCalendar = Calendar.getInstance()
            myCalendar.set(Calendar.YEAR, selectedyear)
            myCalendar.set(Calendar.MONTH, selectedmonth)
            myCalendar.set(Calendar.DAY_OF_MONTH, selectedday)

            val sdf = SimpleDateFormat(myFormatDate, Locale.getDefault())
            if (myCalendar.time.after(mcurrentDate.time)) {
                daySelected = selectedday
                monthSelected = selectedmonth
                yearSelected = selectedyear
                editTextDate?.setText(sdf.format(myCalendar.time))
            } else {
                Toast.makeText(activity, "Date selected is not valid", Toast.LENGTH_SHORT).show()
                editTextDate?.setText("")
            }
        }, mYear, mMonth, mDay)
        mDatePicker.show()
    }

    override fun onFailureCreateEvent() {
        editTextTitle?.setText("")
        editTextDesc?.setText("")
        editTextDate?.setText("")
        editTextTime?.setText("")
        Toast.makeText(activity, "Failure create event", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessCreateEvent(event: Event, b: Boolean) {
        editTextTitle?.setText("")
        editTextDesc?.setText("")
        editTextDate?.setText("")
        editTextTime?.setText("")
        Toast.makeText(activity, "Success create " + event.name, Toast.LENGTH_SHORT).show()
    }
}