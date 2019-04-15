package com.example.unibiz.Utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unibiz.Model.Supplier
import com.example.unibiz.R
import java.util.Date
import java.text.DateFormat


class SupplierRecyclerView(context: Context?, private var mData: List<Supplier>?) : RecyclerView.Adapter<SupplierRecyclerView.ViewHolder>() {
    private val mInflater: LayoutInflater

    init {
        mInflater = LayoutInflater.from(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierRecyclerView.ViewHolder {
        val view = mInflater.inflate(R.layout.list_item_product_supplier, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SupplierRecyclerView.ViewHolder, position: Int) {
        val supplier = mData!![position]
        holder.bind(supplier)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    fun setData(data: List<Supplier>) {
        mData = data
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var SupplierName: TextView = itemView.findViewById(R.id.list_item_name)
        private var SupplierPhone: TextView = itemView.findViewById(R.id.list_item_price)
        private var SupplierDate: TextView = itemView.findViewById(R.id.list_item_date)
        private var mSupplier: Supplier? = null


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {

        }

        internal fun bind(supplier: Supplier) {
            val d : Date = supplier.dateVisit
            val date = DateFormat.getDateInstance(
                    DateFormat.SHORT).format(d)
            mSupplier = supplier
            SupplierName.text = mSupplier!!.name
            SupplierPhone.text = mSupplier!!.phone
            SupplierDate.text = date
        }
    }
}
