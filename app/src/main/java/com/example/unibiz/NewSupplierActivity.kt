package com.example.unibiz

import android.app.DatePickerDialog
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unibiz.DB.DatabaseOperation
import com.example.unibiz.Model.Items
import com.example.unibiz.Model.Supplier
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_new_supplier.*
import java.text.DateFormat
import java.util.*

class NewSupplierActivity : AppCompatActivity() {

    private var mDateOut = Date()
    private var mDateCome = Date()
    private var mDateVisit = Date()
    private var Items_count = 0
    private val allEds = ArrayList<TextInputEditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_supplier)

        toolbar_suppl.setNavigationOnClickListener {
            finish()
        }

        setupDate()
        setupButtons()
    }

    private fun setupButtons() {
        btn_suppl_create_new_item.setOnClickListener {
            create_new_item()
        }
        btn_suppl_delete_item.setOnClickListener {
            delete_item()
        }
        btn_supplier_pick_data.setOnClickListener {
            startDatePicker(mDateCome,2)
        }
        btn_supplier_pick_dataout.setOnClickListener {
            startDatePicker(mDateOut,1)
        }
        btn_supplier_pick_datavisit.setOnClickListener {
            startDatePicker(mDateVisit,3)
        }
        supplier_save_btn.setOnClickListener {
            saveInfo()
        }

    }

    private fun saveInfo() {
        if (checkFields()){
            Toast.makeText(this,getString(R.string.msg_not_all_fields_filled),Toast.LENGTH_SHORT).show()
        }else{

            val uuid  = UUID.randomUUID()
            val uuid1 = UUID.randomUUID()
            val new_suppl = Supplier(uuid)
            new_suppl.name= supplier_name.text.toString()
            new_suppl.phone= supplier_phone.text.toString()
            new_suppl.dateCome =mDateCome
            new_suppl.dateOut = mDateOut
            new_suppl.dateVisit = mDateVisit
            new_suppl.id_items = uuid1

            saveItems(uuid1)
            try {
                DatabaseOperation.get(this)
                        .add(new_suppl)
                Toast.makeText(this,getString(R.string.saved),Toast.LENGTH_SHORT).show()
                this.finish()
            }catch (e: SQLiteException){
                Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveItems(item_id:UUID) {
        if (!TextUtils.isEmpty(supplier_item_1.text)) {
            val items = Items()
            items.id_UUID = item_id
            items.item = supplier_item_1.text.toString()
        }
        if (Items_count >= 1) {
            for (i in 1 until Items_count) {
                val items = Items()
                items.id_UUID = item_id
                items.item = allEds[i - 1].text!!.toString()
            }
        }
    }

    private fun checkFields(): Boolean {
        return TextUtils.isEmpty(supplier_name.text)&&TextUtils.isEmpty(supplier_phone.text)
    }

    private fun delete_item() {
        if (Items_count < 1) {
            Toast.makeText(this, getString(R.string.nothing_to_delete), Toast.LENGTH_SHORT).show()
        } else {
            extra_items_layout.removeViewAt(extra_items_layout.childCount - 1)
            Items_count--
        }
    }

    private fun create_new_item() {
        Items_count++
        val et = TextInputEditText(this)

        val param = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT + 80)
        param.setMargins(5, 15, 5, 0)

        allEds.add(et)
        et.layoutParams = param
        et.hint = getString(R.string.item_label) + Items_count
        et.id = Items_count
        extra_items_layout.addView(et)
    }

    private fun setupDate() {
        val calendar = Calendar.getInstance()
        val startYear = calendar.get(Calendar.YEAR)
        val startMonth = calendar.get(Calendar.MONTH)
        val startDay = calendar.get(Calendar.DAY_OF_MONTH)
        mDateOut = GregorianCalendar(startYear, startMonth, startDay).time
        mDateCome = GregorianCalendar(startYear, startMonth, startDay).time
        mDateVisit = GregorianCalendar(startYear, startMonth, startDay).time
        setupDataComeText(mDateCome)
        setupDataOutText(mDateOut)
        setupDataVisitText(mDateVisit)
    }

    private fun startDatePicker(date: Date, x: Int) {

        val calendar = Calendar.getInstance()
        val Year = calendar.get(Calendar.YEAR)
        val Month = calendar.get(Calendar.MONTH)
        val Day = calendar.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(
                this, { view, year, month, dayOfMonth ->
            if (x==1) {
                mDateOut = GregorianCalendar(year, month, dayOfMonth).time
                setupDataOutText(mDateOut)
            } else if (x==2) {
                mDateCome = GregorianCalendar(year, month, dayOfMonth).time
                setupDataComeText(mDateCome)
            }else if (x==3){
                mDateVisit = GregorianCalendar(year, month, dayOfMonth).time
                setupDataVisitText(mDateVisit)
            }

        }, Year, Month, Day)
        dialog.show()
    }

    private fun setupDataOutText(date: Date) {
        val s = DateFormat.getDateInstance(
                DateFormat.SHORT).format(date)
        new_supplier_date_out.setText(s)
    }

    private fun setupDataComeText(date: Date) {
        val s = DateFormat.getDateInstance(
                DateFormat.SHORT).format(date)
        new_supplier_date.setText(s)
    }

    private fun setupDataVisitText(date: Date) {
        val s = DateFormat.getDateInstance(
                DateFormat.SHORT).format(date)
        new_supplier_date_visit.setText(s)
    }

}
