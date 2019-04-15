package com.example.unibiz.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.unibiz.R;
import com.example.unibiz.Utils.ImageUtils;

import java.util.Date;
import java.util.UUID;

import static com.example.unibiz.DB.DBSchema.CategoryTable;
import static com.example.unibiz.DB.DBSchema.ClientTable;
import static com.example.unibiz.DB.DBSchema.EmployeTable;
import static com.example.unibiz.DB.DBSchema.ItemsTable;
import static com.example.unibiz.DB.DBSchema.ProductTable;
import static com.example.unibiz.DB.DBSchema.SupplierTable;


public class CreationDataBase extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "datebase.db";
    private Context mContext;
    public CreationDataBase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Client table
        db.execSQL("create table " + ClientTable.NAME + "(" +
                ClientTable.Cols._ID + " integer primary key autoincrement, " +

                ClientTable.Cols.C_UUID + ", " +
                ClientTable.Cols.NAME + ", " +
                ClientTable.Cols.PRICE + ", " +
                ClientTable.Cols.ID_CAT + " REFERENCES "+  CategoryTable.NAME + " ( " + CategoryTable.Cols.C_UUID + " ) "+ ", " +
                ClientTable.Cols.ID_EMPL + " references "+ EmployeTable.NAME + " ( " + EmployeTable.Cols.E_UUID + " ) " + ", " +
                ClientTable.Cols.ID_ITEMS + " references "+ ItemsTable.NAME + " ( " + ItemsTable.Cols.ID_UUID + " ) " + ", " +
                ClientTable.Cols.MODEL + ", " +
                ClientTable.Cols.NOMER + ", " +
                ClientTable.Cols.IMEI + ", " +
                ClientTable.Cols.VISIT_DATE + " DATE , " +
                ClientTable.Cols.DATE_BORN +
                " ) "
        );


        //Product table
        db.execSQL(
                "create table " + ProductTable.NAME + "(" +
                        ProductTable.Cols._ID + " integer primary key autoincrement, " +

                        ProductTable.Cols.P_UUID + ", " +
                        ProductTable.Cols.NAME + ", " +
                        ProductTable.Cols.IMEI_CODE + "," +
                        ProductTable.Cols.COUNT + "," +
                        ProductTable.Cols.PRICE + "," +
                        ProductTable.Cols.ID_EMPL + " REFERENCES "+EmployeTable.NAME + " ( " + EmployeTable.Cols.E_UUID + " ) " + ", " +
                        ProductTable.Cols.ID_SUPL + " references "+   SupplierTable.NAME + " ( " + SupplierTable.Cols.S_UUID + " ) " + ", " +
                        ProductTable.Cols.ID_ITEMS + " references "+ ItemsTable.NAME + " ( " + ItemsTable.Cols.ID_UUID + " ) " + ", " +
                        ProductTable.Cols.DATE + " date " +
                        ")"
        );

        //   Category table
        db.execSQL(
                "create table " + CategoryTable.NAME + "("+
                        CategoryTable.Cols._ID + " integer primary key autoincrement, " +

                        CategoryTable.Cols.C_UUID + ", " +
                        CategoryTable.Cols.NAME + ", " +
                        CategoryTable.Cols.PRICE + ", " +
                        CategoryTable.Cols.DATE + ", " +
                        CategoryTable.Cols.IMAGE  + ", "+
                        "unique ("+ CategoryTable.Cols.NAME+")"+
                        ")"
        );


        //Items table
        db.execSQL(
                "create table "+ ItemsTable.NAME + "("+
                        ItemsTable.Cols._ID + " integer primary key autoincrement, "+

                        ItemsTable.Cols.ID_UUID +", " +
                        ItemsTable.Cols.ITEM +
                        ")"
        );

        // Employe table
        db.execSQL(
                "create table "+ EmployeTable.NAME + "("+
                       EmployeTable.Cols._ID +  " integer primary key autoincrement, "+

                       EmployeTable.Cols.E_UUID + ", " +
                       EmployeTable.Cols.NAME + ", " +
                       EmployeTable.Cols.ID_ITEMS + " references "+ ItemsTable.NAME + " ( " + ItemsTable.Cols.ID_UUID + " ) " + ", " +
                       EmployeTable.Cols.JOB + ", " +
                       EmployeTable.Cols.SEX + ", "+
                       EmployeTable.Cols.IMAGE + ", "+
                       EmployeTable.Cols.NOMER + ", "+
                       EmployeTable.Cols.EMAIL + ", "+
                       EmployeTable.Cols.DATE_BORN + ", "+
                       EmployeTable.Cols.DATE_COME + ", "+
                       EmployeTable.Cols.DATE_OUT + ", "+
                       EmployeTable.Cols.ADDRESS +
                        ")"
        );

        // Supplier table
        db.execSQL(
                "create table "+ SupplierTable.NAME + "("+
                        SupplierTable.Cols._ID +  " integer primary key autoincrement, "+
                        SupplierTable.Cols.S_UUID + ", " +
                        SupplierTable.Cols.NAME + ", " +
                        SupplierTable.Cols.ORGANIZATION + ", " +
                        SupplierTable.Cols.ID_ITEMS + " references "+ ItemsTable.NAME + " ( " + ItemsTable.Cols.ID_UUID + " ) " + ", " +
                        SupplierTable.Cols.PHONE + ", "+
                        SupplierTable.Cols.EMAIL + ", "+
                        SupplierTable.Cols.ADDRESS + ", "+
                        SupplierTable.Cols.VISIT_DATE + " date, "+
                        SupplierTable.Cols.DATE_COME + " date, "+
                        SupplierTable.Cols.DATE_OUT + " date "+
                        ")"
        );

        addDefaultValues(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + SupplierTable.NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + ClientTable.NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + ProductTable.NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + EmployeTable.NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + ItemsTable.NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + CategoryTable.NAME);
        onCreate(db);
    }


    private void addDefaultValues(SQLiteDatabase database){

        ContentValues values = new ContentValues();
        values.put(CategoryTable.Cols.C_UUID, UUID.randomUUID().toString());
        values.put(CategoryTable.Cols.NAME  ,"default");
        values.put(CategoryTable.Cols.PRICE ,"200");
        values.put(CategoryTable.Cols.DATE  ,new Date().getTime());
        values.put(CategoryTable.Cols.IMAGE , ImageUtils.imageViewToByte(mContext.getDrawable(R.drawable.img_add_klient)));
        database.insert(CategoryTable.NAME,null,values);


        ContentValues valuesEmploye = new ContentValues();
        valuesEmploye.put(EmployeTable.Cols.E_UUID   ,UUID.randomUUID().toString());
        valuesEmploye.put(EmployeTable.Cols.NAME     ,"default");
        valuesEmploye.put(EmployeTable.Cols.ID_ITEMS   ,UUID.randomUUID().toString());
        valuesEmploye.put(EmployeTable.Cols.JOB      ,"default");
        valuesEmploye.put(EmployeTable.Cols.SEX      ,"default");
        valuesEmploye.put(EmployeTable.Cols.IMAGE     ,ImageUtils.imageViewToByte(mContext.getDrawable(R.drawable.img_add_klient)));
        valuesEmploye.put(EmployeTable.Cols.NOMER    ,"default");
        valuesEmploye.put(EmployeTable.Cols.EMAIL    ,"default");
        valuesEmploye.put(EmployeTable.Cols.DATE_BORN,new Date().getTime());
        valuesEmploye.put(EmployeTable.Cols.DATE_COME,new Date().getTime());
        valuesEmploye.put(EmployeTable.Cols.DATE_OUT ,new Date().getTime());
        valuesEmploye.put(EmployeTable.Cols.ADDRESS  ,"default");

        database.insert(EmployeTable.NAME,null,valuesEmploye);

        ContentValues valuesSupplier = new ContentValues();
        valuesSupplier.put(SupplierTable.Cols.S_UUID   ,UUID.randomUUID().toString());
        valuesSupplier.put(SupplierTable.Cols.NAME     ,"default");
        valuesSupplier.put(SupplierTable.Cols.ORGANIZATION      ,"default");
        valuesSupplier.put(SupplierTable.Cols.ID_ITEMS   ,UUID.randomUUID().toString());
        valuesSupplier.put(SupplierTable.Cols.PHONE      ,"default");
        valuesSupplier.put(SupplierTable.Cols.EMAIL    ,"default");
        valuesSupplier.put(SupplierTable.Cols.ADDRESS    ,"default");
        valuesSupplier.put(SupplierTable.Cols.VISIT_DATE    ,new Date().getTime());
        valuesSupplier.put(SupplierTable.Cols.DATE_COME,"");
        valuesSupplier.put(SupplierTable.Cols.DATE_OUT ,"");

        database.insert(SupplierTable.NAME,null,valuesSupplier);
    }
}
