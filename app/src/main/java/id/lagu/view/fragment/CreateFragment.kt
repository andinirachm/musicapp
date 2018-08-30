package id.lagu.view.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.lagu.R
import kotlinx.android.synthetic.main.fragment_create.*
import java.text.SimpleDateFormat
import java.util.*

class CreateFragment : Fragment() {

    companion object {
        fun newInstance(): CreateFragment {
            var fragment = CreateFragment()
            var args = Bundle()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_pick_date.setOnClickListener {
            val mcurrentDate = Calendar.getInstance()
            var mYear = mcurrentDate.get(Calendar.YEAR)
            var mMonth = mcurrentDate.get(Calendar.MONTH)
            var mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH)

            val mDatePicker = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { datepicker, selectedyear, selectedmonth, selectedday ->
                val myCalendar = Calendar.getInstance()
                myCalendar.set(Calendar.YEAR, selectedyear)
                myCalendar.set(Calendar.MONTH, selectedmonth)
                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday)
                val myFormat = "dd/MM/yy" //Change as you need
                val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)
                edit_text_event_date.setText(sdf.format(myCalendar.getTime()))

                mDay = selectedday
                mMonth = selectedmonth
                mYear = selectedyear
            }, mYear, mMonth, mDay)
            mDatePicker.show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_create, container, false)
        return rootView
    }
}