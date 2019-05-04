package com.example.unibiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.unibiz.Utils.Messages
import kotlinx.android.synthetic.main.activity_report.*

class ReportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        report_revenue.setOnClickListener {
            val intent = Intent(this@ReportActivity,ReportDetailActivity::class.java)
            intent.putExtra(Messages.message,Messages.client)
            startActivity(intent)
        }
        report_costs.setOnClickListener {
            val intent = Intent(this@ReportActivity,ReportDetailActivity::class.java)
            intent.putExtra(Messages.message,Messages.product)
            startActivity(intent)
        }
        report_profit.setOnClickListener {
            val intent = Intent(this@ReportActivity,ReportDetailActivity::class.java)
            intent.putExtra(Messages.message,Messages.profit)
            startActivity(intent)
        }
    }
}
