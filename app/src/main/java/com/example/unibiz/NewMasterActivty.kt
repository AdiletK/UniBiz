package com.example.unibiz

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unibiz.DB.DatabaseOperation
import com.example.unibiz.Model.Employe
import com.example.unibiz.Model.Items
import com.example.unibiz.Utils.ImageUtils
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_new_master_activty.*
import kotlinx.android.synthetic.main.activity_new_product.*
import java.io.IOException
import java.text.DateFormat
import java.util.*

class NewMasterActivty : AppCompatActivity() {

    private var mDateBorn = Date()
    private var mDateCome = Date()
    private var PICK_IMAGE_REQUEST_MASTER:Int = 111
    private var Items_count = 0
    private val allEds = ArrayList<TextInputEditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_master_activty)
        setupViews()

        toolbar_master.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupViews() {
        setupDate()
        setupDataBornText(mDateBorn)
        setupDataComeText(mDateCome)

        btn_master_pick_data.setOnClickListener {
            startDatePicker(mDateBorn,true)
        }
        btn_master_pick_data_come.setOnClickListener {
            startDatePicker(mDateCome,false)
        }
        master_image.setOnClickListener {
            getImageFromGallery()
        }

        master_btn_create_new_item.setOnClickListener {
            add_new_item()
        }
        master_btn_delete.setOnClickListener {
            remove_item()
        }
        master_save_btn.setOnClickListener {
            saveInfo()
        }
    }

    private fun saveInfo() {
        if (!TextUtils.isEmpty(master_name.text) && !TextUtils.isEmpty(master_nomer.text)){
            val uuid : UUID= UUID.randomUUID()
            val id_item : UUID= UUID.randomUUID()
            val new_master  = Employe(uuid)
            new_master.name = master_name.text.toString()
            new_master.nomer = master_nomer.text.toString()
            new_master.dateBorn = mDateBorn
            new_master.dateCome = mDateCome
            new_master.image = ImageUtils.imageViewToByte(master_image)
            new_master.id_items = id_item
            new_master.dateOut = Date()

            if (!TextUtils.isEmpty(master_address.text))
                new_master.adress = master_address.text.toString()
            if (!TextUtils.isEmpty(master_email.text))
                new_master.email = master_email.text.toString()
            if (!TextUtils.isEmpty(master_job.text))
                new_master.job = master_job.text.toString()
            if (radio_btn_man.isChecked) {
                new_master.sex = radio_btn_man.text.toString()
            } else
                new_master.sex = radio_btn_woman.text.toString()


            if (!TextUtils.isEmpty(master_item_1.text)) {
                val items = Items()
                items.id_UUID = id_item
                items.item = master_item_1.text.toString()
            }
            if (Items_count > 1) {
                for (i in 1 until Items_count) {
                    val items = Items()
                    items.id_UUID = id_item
                    items.item = allEds[i - 1].text!!.toString()
                }
            }
            try {
                DatabaseOperation.get(this)
                        .add(new_master)
                Toast.makeText(this,getString(R.string.saved),Toast.LENGTH_SHORT).show()
                finish()
            }catch (ex: SQLiteException){
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            }
        }else
            Toast.makeText(this,"Not all fields are filled",Toast.LENGTH_SHORT).show()
    }


    private fun setupDate(){
        val calendar = Calendar.getInstance()
        val startYear = calendar.get(Calendar.YEAR)
        val startMonth = calendar.get(Calendar.MONTH)
        val startDay = calendar.get(Calendar.DAY_OF_MONTH)
        mDateBorn = Date(startYear,startMonth,startDay)
        mDateCome = Date(startYear,startMonth,startDay)
    }
    private fun startDatePicker( date:Date,x:Boolean) {

        val dialog = DatePickerDialog(
                this, { view, year, month, dayOfMonth ->
            if (x) {
                mDateBorn = Date(year, month, dayOfMonth)
                setupDataBornText(mDateBorn)
            }
            else {
                mDateCome = Date(year, month, dayOfMonth)
                setupDataComeText(mDateCome)
            }

        }, date.year, date.month, date.day)
        dialog.show()
    }
    private fun setupDataBornText(date: Date) {
        val s = DateFormat.getDateInstance(
                DateFormat.SHORT).format(date)
        master_date_born.setText(s)
    }
    private fun setupDataComeText(date: Date) {
        val s = DateFormat.getDateInstance(
                DateFormat.SHORT).format(date)
        master_date_come.setText(s)
    }
    private fun getImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture_label)), PICK_IMAGE_REQUEST_MASTER)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_MASTER && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                System.out.println(bitmap.byteCount)
                val resizedBitmap = resizeBitmap(bitmap,600,580)
                System.out.println(resizedBitmap.byteCount)
                master_image.setImageBitmap(resizedBitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    private fun resizeBitmap(bitmap: Bitmap, width:Int, height:Int): Bitmap {
        return Bitmap.createScaledBitmap(
                bitmap,
                width,
                height,
                true
        )
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
        extra_items_layout_master.addView(et)
    }

    private fun remove_item() {
        if (Items_count < 1) {
            Toast.makeText(this, getString(R.string.nothing_to_delete), Toast.LENGTH_SHORT).show()
        } else {
            extra_items_layout_master.removeViewAt(extra_items_layout.childCount - 1)
            Items_count--
        }
    }
}
