package com.example.unibiz

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.unibiz.DB.DatabaseOperation
import com.example.unibiz.Model.Category
import com.example.unibiz.Utils.ImageUtils
import kotlinx.android.synthetic.main.dialog_new_category.*
import java.io.IOException
import java.util.*


class NewCategoryDialog : DialogFragment()
{
    var PICK_IMAGE_REQUEST:Int = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL,R.style.MaterialTheme)
    }

    companion object {
        fun display(fragmentManager: FragmentManager): NewCategoryDialog {
            val newCategoryDialog = NewCategoryDialog()
            newCategoryDialog.show(fragmentManager, TAG)
            return newCategoryDialog
        }
    }

    override fun onStart() {
        super.onStart()
        val v_dialog :Dialog? = dialog

        if (v_dialog !=null){
            val width : Int = ViewGroup.LayoutParams.MATCH_PARENT
            val height :Int = ViewGroup.LayoutParams.MATCH_PARENT
            v_dialog.window.setLayout(width, height)
            v_dialog.window.setWindowAnimations(R.style.AppTheme_Slide)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)
         val view:View = inflater.inflate(R.layout.dialog_new_category,container,false)

         return view
    }

    private fun getImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture_label)), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.getContentResolver(), uri)
                System.out.println(bitmap.byteCount)
                val resizedBitmap = resizeBitmap(bitmap,660,600)
                category_image.setImageBitmap(resizedBitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_с.setNavigationOnClickListener { v -> dismiss() }
        toolbar_с.inflateMenu(R.menu.example_dialog)
        toolbar_с.setOnMenuItemClickListener { item ->
            dismiss()
            true
        }
        category_image.setImageResource(R.drawable.img_add_klient)
        img_chooser.setOnClickListener {
            getImageFromGallery()
        }
        btn_save_category.setOnClickListener {
            saveInfo()
        }
    }

    private fun saveInfo(){
        if (!TextUtils.isEmpty(txt_category_title.text) && !TextUtils.isEmpty(txt_category_price.text)) {
            category_progress.show()
            val category = Category()
            category.id = UUID.randomUUID()
            category.name = txt_category_title.text.toString()
            category.price = txt_category_price.text.toString().toDouble()
            category.date = Date()
            category.image = ImageUtils.imageViewToByte(category_image)
            val addIntoDB = DatabaseOperation.get(activity)
            addIntoDB.add(category)
            category_progress.hide()
            dismiss()
        }
        else{
            Toast.makeText(activity,"Please fill the fields",Toast.LENGTH_LONG).show()
        }
    }

    private fun resizeBitmap(bitmap:Bitmap, width:Int, height:Int):Bitmap{
        return Bitmap.createScaledBitmap(
                bitmap,
                width,
                height,
                true
        )
    }
}
