package com.example.unibiz.AllDataDialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unibiz.DB.DatabaseOperation
import com.example.unibiz.Model.Client
import com.example.unibiz.R
import com.example.unibiz.Utils.ClientRecyclerView
import kotlinx.android.synthetic.main.dialog_views_for_all_data.*

class ClientViewDialogs:DialogFragment(){

    var mContext  = context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MaterialTheme)
    }
    companion object {
        fun display(fragmentManager: FragmentManager): ClientViewDialogs {
            val newCategoryDialog = ClientViewDialogs()
            newCategoryDialog.show(fragmentManager, ContentValues.TAG)
            return newCategoryDialog
        }
    }

    override fun onStart() {
        super.onStart()
        val v_dialog : Dialog? = dialog
        if (v_dialog !=null){
            val width : Int = ViewGroup.LayoutParams.MATCH_PARENT
            val height :Int = ViewGroup.LayoutParams.MATCH_PARENT
            v_dialog.window.setLayout(width, height)
            v_dialog.window.setWindowAnimations(R.style.AppTheme_Slide)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.dialog_views_for_all_data,container,false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_views.title = getString(R.string.dialog_title_client_views)
        toolbar_views.setNavigationOnClickListener { v -> dismiss() }
        toolbar_views.setOnMenuItemClickListener { item ->
            dismiss()
            true
        }
        progressBar.visibility=View.VISIBLE
        val handler = Handler()
        handler.postDelayed({  Thread{  BackgroundTask().execute()}.start() }, 200)


    }


    @SuppressLint("StaticFieldLeak")
    inner  class BackgroundTask : AsyncTask<String, Int, List<Client>>() {
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility=View.VISIBLE
        }

        override fun doInBackground(vararg params: String): List<Client> {

            return DatabaseOperation.get(mContext).clients

        }

        override fun onPostExecute(result: List<Client>?) {
            super.onPostExecute(result)
            recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            val mClients = ClientRecyclerView(context, result, LayoutInflater.from(context),null)
            recycler_view.adapter = mClients

            progressBar.visibility=View.INVISIBLE


        }
    }
}