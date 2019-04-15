package com.example.unibiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.unibiz.AllDataDialogs.ClientViewDialogs
import com.example.unibiz.Utils.Messages

class ViewDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data)

        val bundle = intent.extras
        val message = bundle!!.getString(Messages.message)
        if (message == Messages.client){
            ClientViewDialogs.display(supportFragmentManager)
        }
    }
}
