package com.example.unibiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.unibiz.CalendarItems.DynamicFragment
import com.example.unibiz.Utils.Messages
import kotlinx.android.synthetic.main.activity_calendar.*
import java.util.*

class CalendarActivity : AppCompatActivity() {

    lateinit var mDate:Date
    var flag = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        toolbar_calendar.setNavigationOnClickListener {
            finish()
        }

        setSupportActionBar(toolbar_calendar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val date = GregorianCalendar(year, month, day).time

        mDate = date
        default(date,true)

        calendar_view.setOnDateChangeListener { _calendarView, year, month, day ->
            mDate = GregorianCalendar(year, month, day).time
            if (flag) {
                default(mDate, flag)
            }
            else{
                default(mDate,flag)
            }
        }


        material_btn.setOnClickListener {
            if (flag) {
                flag = false
                default(date, flag)
            }
        }

        client_btn.setOnClickListener {
            if (!flag) {
                flag = true
                default(date, flag)
            }
        }
    }

    private fun default(date: Date,flag: Boolean) {
        val fm = supportFragmentManager

        if (flag) {
            val fragment = DynamicFragment.newInstance(date, true, "")
            fm.beginTransaction()
                    .replace(R.id.day_fragment, fragment)
                    .commit()
        }else{
            val fragment = DynamicFragment.newInstance(date, false, "")
            fm.beginTransaction()
                    .replace(R.id.day_fragment, fragment)
                    .commit()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_calendar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.show_client->{
                showClient()
            }
            R.id.show_material->{
                showMaterial()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showMaterial() {
        val intent = Intent(this, ViewDataActivity::class.java)
        intent.putExtra(Messages.client,mDate)
        intent.putExtra(Messages.message, Messages.product)
        startActivity(intent)
    }

    private fun showClient() {
        val intent = Intent(this, ViewDataActivity::class.java)
        intent.putExtra(Messages.client,mDate)
        intent.putExtra(Messages.message, "")

        startActivity(intent)
    }
}
