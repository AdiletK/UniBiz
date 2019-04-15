package com.example.unibiz

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.database.SQLException
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unibiz.DB.DatabaseOperation
import com.example.unibiz.Model.Employe
import com.example.unibiz.Model.Items
import com.example.unibiz.Model.Product
import com.example.unibiz.Model.Supplier
import com.example.unibiz.Utils.ScannerUtils.BarcodeCaptureActivity
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_new_product.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class NewProductActivity : AppCompatActivity() {

    private val allEds = ArrayList<TextInputEditText>()
    private var Items_count = 0
    private val RC_BARCODE_CAPTURE = 9001
    private var mDate = Date()
    private var id_empl: UUID? = null
    private var id_suppl:UUID? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)

        setupButtons()
        setupDate()

        toolbar_product.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupButtons() {
        btn_product_pick_data.setOnClickListener {
            startDatePicker()
        }
        btn_prod_create_new_item.setOnClickListener{
            add_new_item()
        }
        btn_prod_delete_item.setOnClickListener {
            remove_item()
        }
        product_scaner_btn.setOnClickListener {
            startCamera()
        }
        product_save_btn.setOnClickListener {
            checkInfo()
        }
        setupSpinners()
    }



    private fun checkInfo() {
        if (!TextUtils.isEmpty(product_name.text) || !TextUtils.isEmpty(product_count.text) ||
                !TextUtils.isEmpty(product_price.text)){
            @SuppressLint("SimpleDateFormat") val sc = SimpleDateFormat("yyyy-MM-dd").format(mDate)
            val uuid = UUID.randomUUID()
            val item_id = UUID.randomUUID()
            val new_product = Product(uuid)
            new_product.name = product_name.text.toString()
            new_product.count =product_count.text.toString()
            new_product.price = product_price.text.toString()
            new_product.date =  sc
            new_product.id_empl =id_empl
            new_product.id_supl = id_suppl
            new_product.id_items = item_id
            new_product.imei_code = product_imei.text.toString()

            if (!TextUtils.isEmpty(product_item_1.text)) {
                val items = Items()
                items.id_UUID = item_id
                items.item = product_item_1.text.toString()
            }
            if (Items_count > 1) {
                for (i in 1 until Items_count) {
                    val items = Items()
                    items.id_UUID = item_id
                    items.item = allEds[i - 1].text!!.toString()
                }
            }
            try {
                DatabaseOperation.get(this)
                        .add(new_product)
                Toast.makeText(this,getString(R.string.saved),Toast.LENGTH_SHORT).show()
                this.finish()
            }catch (e: SQLException){
                Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun setupDate(){
        val calendar = Calendar.getInstance()
        val startYear = calendar.get(Calendar.YEAR)
        val starthMonth = calendar.get(Calendar.MONTH)
        val startDay = calendar.get(Calendar.DAY_OF_MONTH)
        mDate = GregorianCalendar(startYear, starthMonth, startDay).time
        setupDataText(mDate)
    }
    private fun startDatePicker() {
        val calendar = Calendar.getInstance()
        val Year = calendar.get(Calendar.YEAR)
        val Month = calendar.get(Calendar.MONTH)
        val Day = calendar.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(
               this, { view, year, month, dayOfMonth ->
            mDate = GregorianCalendar(year, month, dayOfMonth).time
            setupDataText(mDate)
        },Year , Month, Day)
        dialog.show()
    }

    private fun add_new_item() {
        // add edittext
        Items_count++
        val et = TextInputEditText(this)

        val param = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT + 80)
        param.setMargins(5, 15, 5, 0)

        allEds.add(et)
        et.layoutParams = param
        et.hint = getString(R.string.item_label) + Items_count
        et.id = Items_count
        et.setTextColor(Color.WHITE)
        et.setHintTextColor(resources.getColor(R.color.semi_gray))

        extra_items_layout.addView(et)
    }

    private fun remove_item() {
        if (Items_count < 1) {
            Toast.makeText(this, getString(R.string.nothing_to_delete), Toast.LENGTH_SHORT).show()
        } else {
            extra_items_layout.removeViewAt(extra_items_layout.childCount - 1)
            Items_count--
        }
    }

    private fun startCamera() {
        val intent = Intent(this, BarcodeCaptureActivity::class.java)
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true)
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false)
        startActivityForResult(intent, RC_BARCODE_CAPTURE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    val barcode = data.getParcelableExtra<Barcode>(BarcodeCaptureActivity.BarcodeObject)
                    product_imei.setText(barcode.displayValue)
                } else {
                    Toast.makeText(this, "No barcode captured, intent data is null",Toast.LENGTH_SHORT).show()
                }
            }
        }  else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setupDataText(date: Date) {
        val s = DateFormat.getDateInstance(
                DateFormat.SHORT).format(date)
        product_date.setText(s)
    }
    private fun setupSpinners() {
        //employer
        val Lab = DatabaseOperation.get(this)
        val employes = Lab.employes

        product_empl_spn.adapter = ArrayAdapter<Employe>(this, android.R.layout.simple_spinner_dropdown_item, employes)
        product_empl_spn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //something
                id_empl = employes[position].uuid
                System.out.println(position)
                System.out.println( employes[position].uuid)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        //supplier
        val suppliers = Lab.suppliers

        product_suppl_spn.adapter  = ArrayAdapter<Supplier>(this,android.R.layout.simple_spinner_dropdown_item,suppliers)
        product_suppl_spn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                id_suppl = suppliers[position].uuid
            }

        }
    }
}
