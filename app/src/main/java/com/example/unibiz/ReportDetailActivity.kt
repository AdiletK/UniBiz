package com.example.unibiz

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unibiz.DB.DatabaseOperation
import com.example.unibiz.Model.Client
import com.example.unibiz.Model.Product
import com.example.unibiz.Utils.ClientRecyclerView
import com.example.unibiz.Utils.Messages
import com.example.unibiz.Utils.ProductRecyclerView
import kotlinx.android.synthetic.main.activity_report_detail.*
import java.text.SimpleDateFormat
import java.util.*

class ReportDetailActivity : AppCompatActivity() {

    private lateinit var fDate:Date
    private lateinit var sDate:Date
    var mRevenue = 0.0
    var mCosts = 0.0
    var mCountClient= 0
    var mCountMaterial= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_detail)
        val result:Intent = intent
        val name = result.extras[Messages.message]
        when (name) {
            Messages.client -> result_value.text = getString(R.string.revenue)
            Messages.product -> result_value.text = getString(R.string.costs)
            else -> result_value.text = getString(R.string.profit)
        }

        val calendar = Calendar.getInstance()
        val startYear = calendar.get(Calendar.YEAR)
        val startMonth = calendar.get(Calendar.MONTH)
        val startDay = calendar.get(Calendar.DAY_OF_MONTH)
        fDate = GregorianCalendar(startYear, startMonth-1, startDay).time
        sDate = GregorianCalendar(startYear, startMonth, startDay).time
        setupFirstDataText(fDate)
        setupSecondDataText(sDate)

        first_date.setOnClickListener {
            startDatePicker(true)
        }
        second_date.setOnClickListener {
            startDatePicker(false)
        }
        execute_btn.setOnClickListener {
            when (name) {
                Messages.client -> BackgroundTaskClient().execute()
                Messages.product -> BackgroundTaskMaterial().execute()
                else -> {
                    BackgroundTaskRevenue().execute()
                    BackgroundTaskCosts().execute()
                }
            }
        }
    }



    private fun startDatePicker(flag:Boolean) {
        val calendar = Calendar.getInstance()
        val startYear = calendar.get(Calendar.YEAR)
        val starthMonth = calendar.get(Calendar.MONTH)
        val startDay = calendar.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(
                this, { view, year, month, dayOfMonth ->
           val date = GregorianCalendar(year, month, dayOfMonth).time
            if (flag){
                fDate = date
                setupFirstDataText(date)
            }else{
                sDate=date
                setupSecondDataText(date)
            }

        }, startYear, starthMonth, startDay)
        dialog.show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setupFirstDataText(date: Date?) {
        val s = SimpleDateFormat("yyyy-MM-dd").format(date)
        first_date.text = s

    }

    @SuppressLint("SimpleDateFormat")
    private fun setupSecondDataText(date: Date?) {
        val s = SimpleDateFormat("yyyy-MM-dd").format(date)
        second_date.text = s

    }


    @SuppressLint("StaticFieldLeak")
    inner  class BackgroundTaskClient : AsyncTask<String, Int, List<Client>>() {
        @SuppressLint("SimpleDateFormat")
        val date1 = SimpleDateFormat("yyyy-MM-dd").format(fDate)!!
        @SuppressLint("SimpleDateFormat")
        val date2 = SimpleDateFormat("yyyy-MM-dd").format(sDate)!!

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility=View.VISIBLE
        }

        override fun doInBackground(vararg params: String): List<Client> {
            return DatabaseOperation.get(this@ReportDetailActivity).getClients(date1,date2)
        }

        override fun onPostExecute(result: List<Client>?) {
            super.onPostExecute(result)
            val rec: RecyclerView = findViewById(R.id.report_rec)
            rec.layoutManager = LinearLayoutManager(this@ReportDetailActivity, RecyclerView.VERTICAL, false)
            val mClients = ClientRecyclerView(this@ReportDetailActivity, result, LayoutInflater.from(this@ReportDetailActivity),null)
            rec.adapter = mClients
            mRevenue=0.0
            var revenue = 0.0
            if (result != null) {
                for (i in 0 until result.size){
                    revenue += result[i].price.toDouble()
                }
            }
            result_data.text = revenue.toString()
            mRevenue = revenue
            progressBar.visibility = View.INVISIBLE

        }
    }

    @SuppressLint("StaticFieldLeak")
    inner  class BackgroundTaskMaterial : AsyncTask<String, Int, List<Product>>() {
        @SuppressLint("SimpleDateFormat")
        val date1 = SimpleDateFormat("yyyy-MM-dd").format(fDate)!!
        @SuppressLint("SimpleDateFormat")
        val date2 = SimpleDateFormat("yyyy-MM-dd").format(sDate)!!

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility=View.VISIBLE
        }

        override fun doInBackground(vararg params: String): List<Product> {
            return DatabaseOperation.get(this@ReportDetailActivity).getProducts(date1,date2)
        }

        override fun onPostExecute(result: List<Product>?) {
            super.onPostExecute(result)
            val rec: RecyclerView = findViewById(R.id.report_rec)
            rec.layoutManager = LinearLayoutManager(this@ReportDetailActivity, RecyclerView.VERTICAL, false)
            val mClients = ProductRecyclerView(this@ReportDetailActivity, result )
            rec.adapter = mClients
            var costs = 0.0
            mCosts=0.0
            if (result != null) {
                for (i in 0 until result.size){
                    if (!result[i].price.isEmpty()) {
                        costs += result[i].price.toDouble()
                    }
                }
            }
            result_data.text = costs.toString()
            mCosts = costs
            progressBar.visibility = View.INVISIBLE

        }
    }

    @SuppressLint("StaticFieldLeak")
    inner  class BackgroundTaskRevenue : AsyncTask<String, Int, List<Client>>() {
        @SuppressLint("SimpleDateFormat")
        val date1 = SimpleDateFormat("yyyy-MM-dd").format(fDate)!!
        @SuppressLint("SimpleDateFormat")
        val date2 = SimpleDateFormat("yyyy-MM-dd").format(sDate)!!

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility=View.VISIBLE
        }

        override fun doInBackground(vararg params: String): List<Client> {
            return DatabaseOperation.get(this@ReportDetailActivity).getClients(date1,date2)
        }

        override fun onPostExecute(result: List<Client>?) {
            super.onPostExecute(result)
            var revenue = 0.0
            mRevenue=0.0
            mCountClient=0
            if (result != null) {
                for (i in 0 until result.size){
                        revenue += result[i].price
                    mCountClient++
                }
            }
            mRevenue = revenue
            progressBar.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner  class BackgroundTaskCosts : AsyncTask<String, Int, List<Product>>() {
        @SuppressLint("SimpleDateFormat")
        val date1 = SimpleDateFormat("yyyy-MM-dd").format(fDate)!!
        @SuppressLint("SimpleDateFormat")
        val date2 = SimpleDateFormat("yyyy-MM-dd").format(sDate)!!

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility=View.VISIBLE
        }

        override fun doInBackground(vararg params: String): List<Product> {
            return DatabaseOperation.get(this@ReportDetailActivity).getProducts(date1,date2)
        }

        override fun onPostExecute(result: List<Product>?) {
            super.onPostExecute(result)
            var costs = 0.0
            mCosts=0.0
            mCountMaterial=0
            if (result != null) {
                for (i in 0 until result.size){
                    mCountMaterial++
                    if (!result[i].price.isEmpty()) {
                        costs += result[i].price.toDouble()
                    }
                }
            }
            mCosts = costs
            result_data.text = (mRevenue-mCosts).toString()

            main_profit_layout.visibility = View.VISIBLE
            count_orders.text =  mCountClient.toString()
            count_materials.text = mCountMaterial.toString()
            progressBar.visibility = View.INVISIBLE
        }
    }
}
