package com.example.unibiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import com.example.unibiz.CalendarItems.DynamicClientFragment
import kotlinx.android.synthetic.main.activity_calendar.*
import java.util.*

class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        var mDate = GregorianCalendar(year, month, day).time

        default(mDate)

        calendar_view.setOnDateChangeListener { _calendarView, year, month, day ->
            mDate = GregorianCalendar(year, month, day).time
            default(mDate)
        }


    }

    private fun default(date: Date) {
        val fm = supportFragmentManager
        val fragment = DynamicClientFragment.newInstance(date,false,"")

        fm.beginTransaction()
                .replace(R.id.day_fragment, fragment)
                .commit()
    }


}
