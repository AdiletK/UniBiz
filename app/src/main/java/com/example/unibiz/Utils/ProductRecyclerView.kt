package com.example.unibiz.Utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unibiz.Model.Product
import com.example.unibiz.R


class ProductRecyclerView(context: Context?, private var mData: List<Product>?) : RecyclerView.Adapter<ProductRecyclerView.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductRecyclerView.ViewHolder {
        val view = mInflater.inflate(R.layout.list_item_product_supplier, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductRecyclerView.ViewHolder, position: Int) {
        val product = mData!![position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    fun setData(data: List<Product>) {
        mData = data
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var ProductName: TextView = itemView.findViewById(R.id.list_item_name)
        private var ProductPrice: TextView = itemView.findViewById(R.id.list_item_price)
        private var ProductDate: TextView = itemView.findViewById(R.id.list_item_date)
        private var mProduct: Product? = null

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {

        }

        internal fun bind(product: Product) {
            mProduct = product

            ProductName.text = mProduct!!.name
            ProductPrice.text = mProduct!!.price
            ProductDate.text = mProduct!!.date
        }
    }
}
