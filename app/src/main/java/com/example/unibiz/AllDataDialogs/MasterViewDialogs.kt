package com.example.unibiz.AllDataDialogs

import android.app.Dialog
import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unibiz.DB.DatabaseOperation
import com.example.unibiz.R
import com.example.unibiz.Utils.MasterRecyclerView
import kotlinx.android.synthetic.main.dialog_views_for_all_data.*

class MasterViewDialogs:DialogFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MaterialTheme)
    }
    companion object {
        fun display(fragmentManager: FragmentManager): MasterViewDialogs {
            val newCategoryDialog = MasterViewDialogs()
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
        toolbar_views.title = getString(R.string.dialog_title_master_views)
        toolbar_views.setNavigationOnClickListener { v -> dismiss() }
        toolbar_views.setOnMenuItemClickListener { item ->
            dismiss()
            true
        }
        Thread(Runnable {
            getmasters()
        }).start()

    }

    private fun getmasters() {
        val operation = DatabaseOperation.get(context)
        val masters = operation.employes
        recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val mMasters = MasterRecyclerView(context, masters)
        recycler_view.adapter = mMasters
    }
}