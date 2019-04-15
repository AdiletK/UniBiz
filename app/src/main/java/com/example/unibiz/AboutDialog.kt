package com.example.unibiz

import android.app.Dialog
import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.dialog_about.*

class AboutDialog : DialogFragment()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL,R.style.MaterialTheme)
    }

    companion object {
        fun display(fragmentManager: FragmentManager): AboutDialog {
            val newCategoryDialog = AboutDialog()
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
        val view: View = inflater.inflate(R.layout.dialog_about,container,false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { v -> dismiss() }
        toolbar.setOnMenuItemClickListener { item ->
            dismiss()
            true
        }
    }
}
