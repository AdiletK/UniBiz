package com.example.unibiz.CalendarItems

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.unibiz.DB.DatabaseOperation
import com.example.unibiz.Model.Client
import com.example.unibiz.R
import com.example.unibiz.Utils.ClientRecyclerView
import kotlinx.android.synthetic.main.dialog_views_for_all_data.*


import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


class DynamicClientFragment : Fragment() {


    private val ARG_ORDER_DATE = "date_order"
    private val ARG_ORDER_BOOL = "date_bool"
    private val ARG_ORDER_NAME = "name"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_item_list,container,false)

        val date = arguments?.getSerializable(ARG_ORDER_DATE)
        @SuppressLint("SimpleDateFormat") val sc = SimpleDateFormat("yyyy-MM-dd").format(date)

        val operation = DatabaseOperation.get(context).getClients(sc,sc)

        val rec: RecyclerView = view.findViewById(R.id.client_list)
        rec.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val mClients = ClientRecyclerView(context, operation, LayoutInflater.from(context))
        rec.adapter = mClients
        return view
    }

    private fun updateUI(date: Serializable?) {
       val operation = DatabaseOperation.get(context).clients
            if(recycler_view==null){

        }else {
                recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            val mClients = ClientRecyclerView(context, operation, LayoutInflater.from(context))
                recycler_view.adapter = mClients
        }

    }

companion object {
    private val ARG_ORDER_DATE = "date_order"
    private val ARG_ORDER_BOOL = "date_bool"
    private val ARG_ORDER_NAME = "name"
    fun newInstance(date: Date, isCheck: Boolean, name: String): DynamicClientFragment {
        val args = Bundle()
        args.putSerializable(ARG_ORDER_DATE, date)
        args.putSerializable(ARG_ORDER_BOOL, isCheck)
        args.putSerializable(ARG_ORDER_NAME, name)
        val fragment = DynamicClientFragment()

        fragment.arguments = args
        return fragment
    }

}

}
