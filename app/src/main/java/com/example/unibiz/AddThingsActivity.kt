package com.example.unibiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_things.*


class AddThingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_things)

        toolbar_add_activity.setNavigationOnClickListener {
            finish()
        }
        clickEvents()
    }

    private fun clickEvents() {
         add_master.setOnClickListener {
             val masterIntent = Intent(this, NewMasterActivty::class.java)
             val NewMasterActivty_Request_code = 1
             startActivityForResult(masterIntent, NewMasterActivty_Request_code)
         }
        add_category.setOnClickListener {
            NewCategoryDialog.display(supportFragmentManager)
        }
        add_client.setOnClickListener {
            val NewClientActivity_Request_code=4
            startActivityForResult(Intent(this,NewClientActivity::class.java),NewClientActivity_Request_code)
        }
        add_material.setOnClickListener {
            val NewProductActivity_Request_code=2
            startActivityForResult(Intent(this,NewProductActivity::class.java),NewProductActivity_Request_code)
        }
        add_supplier.setOnClickListener {
            val NewSupplierActivity_Request_code =3
            startActivityForResult(Intent(this,NewSupplierActivity::class.java),NewSupplierActivity_Request_code)
        }
    }
}
