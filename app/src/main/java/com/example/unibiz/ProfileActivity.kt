package com.example.unibiz

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unibiz.DB.DatabaseOperation
import com.example.unibiz.Model.Client
import com.example.unibiz.Utils.ClientRecyclerView
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private val mEXTRA_ID = "com.example.kadyrbekov.bloknotforsalon.id"



    companion object {
        private val mEXTRA_ID = "com.example.kadyrbekov.bloknotforsalon.id"
        fun newIntent(packageContext: Context, id: UUID): Intent {
            val intent = Intent(packageContext, ProfileActivity::class.java)
            intent.putExtra(mEXTRA_ID, id)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val id = intent.getSerializableExtra(mEXTRA_ID) as UUID

        val client: Client = DatabaseOperation.get(this).getClient(id)

        setupData(client)
    }

    private fun setupData(client: Client) {
        profile_name.text = client.name
        profile_phone.text = client.nomer
        val bitmap:Bitmap = getBitmap(client.id_category)
        profile_image.setImageBitmap(bitmap)

        val rec: RecyclerView = findViewById(R.id.profile_rec_view)
        rec.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val operation:List<Client> = DatabaseOperation.get(this).getClients(client.name)
        val mClients = ClientRecyclerView(this, operation, LayoutInflater.from(this),null)
        rec.adapter = mClients



        var summat = 0.0
        for (i in 0 until operation.size) {
            summat += operation[i].price
        }
        profile_summa.text= summat.toString()
        profile_visit.text = operation.size.toString()

    }

    private fun getBitmap(id:UUID): Bitmap {
        val category = DatabaseOperation.get(this)
        val selected = category.getCategory(id)
        return BitmapFactory.decodeByteArray(selected!!.image, 0, selected.image.size)
    }
}
