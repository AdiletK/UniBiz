package com.example.unibiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unibiz.AllDataDialogs.*
import kotlinx.android.synthetic.main.activity_alldata.*
import android.content.Intent
import com.example.unibiz.Utils.Messages


class AllDataActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alldata)
        clickListener()

        toolbar_alldata_activity.setNavigationOnClickListener {
            finish()
        }
    }

    private fun clickListener() {
        data_client.setOnClickListener {
            //call Client View dialog
            val intent = Intent(this, ViewDataActivity::class.java)
            intent.putExtra(Messages.message, Messages.client)
            startActivity(intent)

        }
        data_category.setOnClickListener {
            //Category view
            CategoryViewDialogs.display(supportFragmentManager)
        }
        data_master.setOnClickListener {
            //Master view
            MasterViewDialogs.display(supportFragmentManager)
        }
        data_material.setOnClickListener {
            //Material
            ProductViewDialogs.display(supportFragmentManager)
        }
        data_supplier.setOnClickListener {
            //Supplier view
            SupplierViewDialogs.display(supportFragmentManager)
        }
    }


}
