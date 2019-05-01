package com.example.unibiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.unibiz.CalendarItems.DynamicFragment
import com.example.unibiz.Utils.Messages
import kotlinx.android.synthetic.main.list_item_client.*
import java.util.*

class ViewDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val mDate = GregorianCalendar(year, month, day).time
        val fm = supportFragmentManager

        val data = intent.extras[Messages.client] as Date

        val name:String = intent.extras[Messages.message] as String
        when (name) {
            Messages.client -> {
                val fragment = DynamicFragment.newInstance(mDate, true, Messages.client)
                fm.beginTransaction()
                        .replace(R.id.data_fragment, fragment)
                        .commit()
            }
            Messages.product -> {
                val fragment = DynamicFragment.newInstance(data, false, "")
                fm.beginTransaction()
                        .replace(R.id.data_fragment, fragment)
                        .commit()
            }
            else -> {
                val fragment = DynamicFragment.newInstance(data, true, "")
                fm.beginTransaction()
                        .replace(R.id.data_fragment, fragment)
                        .commit()
            }
        }
    }
}
