package com.example.unibiz.CalendarItems

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.unibiz.DB.DatabaseOperation
import com.example.unibiz.R
import com.example.unibiz.Utils.ClientRecyclerView
import com.example.unibiz.Utils.Messages
import com.example.unibiz.Utils.ProductRecyclerView


import java.text.SimpleDateFormat
import java.util.*


class DynamicFragment : Fragment() {


    private val ARG_ORDER_DATE = "date_order"
    private val ARG_ORDER_BOOL = "date_bool"
    private val ARG_ORDER_NAME = "name"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_item_list,container,false)

        val date = arguments?.getSerializable(ARG_ORDER_DATE)
        val text = arguments?.getSerializable(ARG_ORDER_NAME)
        val flag = arguments?.getSerializable(ARG_ORDER_BOOL) as Boolean
        @SuppressLint("SimpleDateFormat") val sc = SimpleDateFormat("yyyy-MM-dd").format(date)
        val rec: RecyclerView = view.findViewById(R.id.client_list)
        rec.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        if (text==Messages.client){
            val operation = DatabaseOperation.get(context).clients
            val mClients = ClientRecyclerView(context, operation, LayoutInflater.from(context))
            rec.adapter = mClients
        }else if (text==Messages.product) {

        }
        else{
            if (flag) {
                val operation = DatabaseOperation.get(context).getClients(sc, sc)
                val mClients = ClientRecyclerView(context, operation, LayoutInflater.from(context))
                rec.adapter = mClients
            } else {
                val operation = DatabaseOperation.get(context).getProducts(sc, sc)
                val materials = ProductRecyclerView(context, operation)
                rec.adapter = materials
            }
        }
        return view
    }



companion object {
    private val ARG_ORDER_DATE = "date_order"
    private val ARG_ORDER_BOOL = "date_bool"
    private val ARG_ORDER_NAME = "name"
    fun newInstance(date: Date, isCheck: Boolean, name: String): DynamicFragment {
        val args = Bundle()
        args.putSerializable(ARG_ORDER_DATE, date)
        args.putSerializable(ARG_ORDER_BOOL, isCheck)
        args.putSerializable(ARG_ORDER_NAME, name)
        val fragment = DynamicFragment()

        fragment.arguments = args
        return fragment
    }

}

}
