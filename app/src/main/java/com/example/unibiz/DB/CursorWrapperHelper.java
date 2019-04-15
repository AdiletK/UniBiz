package com.example.unibiz.DB;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.unibiz.Model.Category;
import com.example.unibiz.Model.Client;
import com.example.unibiz.Model.Employe;
import com.example.unibiz.Model.Items;
import com.example.unibiz.Model.Product;
import com.example.unibiz.Model.Supplier;

import java.util.Date;
import java.util.UUID;

import static com.example.unibiz.DB.DBSchema.CategoryTable;
import static com.example.unibiz.DB.DBSchema.ClientTable;
import static com.example.unibiz.DB.DBSchema.EmployeTable;
import static com.example.unibiz.DB.DBSchema.ItemsTable;
import static com.example.unibiz.DB.DBSchema.ProductTable;
import static com.example.unibiz.DB.DBSchema.SupplierTable;

class CursorWrapperHelper extends CursorWrapper {

    CursorWrapperHelper(Cursor cursor) {
        super(cursor);
    }

    Product getProduct(){
        String uuid  = getString(    getColumnIndex(ProductTable.Cols.P_UUID    ));
        String id_empl = getString(  getColumnIndex(ProductTable.Cols.ID_EMPL   ));
        String id_supl = getString(  getColumnIndex(ProductTable.Cols.ID_SUPL   ));
        String id_items = getString( getColumnIndex(ProductTable.Cols.ID_ITEMS  ));
        String name = getString(     getColumnIndex(ProductTable.Cols.NAME      ));
        String imei_code = getString(getColumnIndex(ProductTable.Cols.IMEI_CODE));
        String count = getString(    getColumnIndex(ProductTable.Cols.COUNT     ));
        String price = getString(    getColumnIndex(ProductTable.Cols.PRICE     ));
        String date = getString(         getColumnIndex(ProductTable.Cols.DATE      ));


        Product product = new Product(UUID.fromString(uuid));
        product.setId_empl(UUID.fromString(id_empl));
        product.setId_supl(UUID.fromString(id_supl));
        product.setId_items(UUID.fromString(id_items));
        product.setName(name);
        product.setImei_code(imei_code);
        product.setCount(count);
        product.setPrice(price);
        product.setDate((date));
        return product;
    }

    Client getClient(){
        String uuid  = getString(getColumnIndex  ( ClientTable.Cols.C_UUID     ));
        String id_empl = getString(getColumnIndex( ClientTable.Cols.ID_EMPL    ));
        String id_cat = getString(getColumnIndex(  ClientTable.Cols.ID_CAT     ));
        String id_items = getString(getColumnIndex(ClientTable.Cols.ID_ITEMS   ));
        String name = getString(getColumnIndex(    ClientTable.Cols.NAME       ));
        String model = getString(getColumnIndex(   ClientTable.Cols.MODEL      ));
        String nomer = getString(getColumnIndex(   ClientTable.Cols.NOMER      ));
        String imei = getString(getColumnIndex(    ClientTable.Cols.IMEI       ));
        long date_born = getLong(getColumnIndex(   ClientTable.Cols.DATE_BORN  ));
        String date_visit = getString(getColumnIndex(  ClientTable.Cols.VISIT_DATE ));
        double price = getLong(getColumnIndex(     ClientTable.Cols.PRICE      ));

        Client client = new Client(UUID.fromString(uuid));
        client.setId_category(UUID.fromString(id_cat));
        client.setId_empl(UUID.fromString(id_empl));
        client.setId_item(UUID.fromString(id_items));
        client.setName(name);
        client.setModel(model);
        client.setNomer(nomer);
        client.setImei(imei);
        client.setDate(new Date(date_born));
        client.setVisitDate((date_visit));
        client.setPrice(price);
        return client;
    }

    Category getCategory(){
        String uuid  = getString(getColumnIndex  (CategoryTable.Cols.C_UUID ));
        String name = getString(getColumnIndex(   CategoryTable.Cols.NAME   ));
        byte[] image = getBlob(getColumnIndex(    CategoryTable.Cols.IMAGE  ));
        Double price = getDouble(getColumnIndex(  CategoryTable.Cols.PRICE  ));
        long date = getLong(getColumnIndex(       CategoryTable.Cols.DATE   ));


        Category category = new Category(UUID.fromString(uuid));
        category.setName(name);
        category.setImage(image);
        category.setPrice(price);
        category.setDate(new Date(date));
        return category;
    }

    Employe getEmploye(){

        String uuid  = getString(getColumnIndex  ( EmployeTable.Cols.E_UUID   ));
        String id_items = getString(getColumnIndex(EmployeTable.Cols.ID_ITEMS ));
        String name = getString(getColumnIndex(    EmployeTable.Cols.NAME     ));
        String job = getString(getColumnIndex(     EmployeTable.Cols.JOB      ));
        String sex = getString(getColumnIndex(     EmployeTable.Cols.SEX      ));
        byte[] image = getBlob(getColumnIndex(     EmployeTable.Cols.IMAGE    ));
        String nomer = getString(getColumnIndex(   EmployeTable.Cols.NOMER    ));
        String email = getString(getColumnIndex(   EmployeTable.Cols.EMAIL    ));
        long date_born = getLong(getColumnIndex(   EmployeTable.Cols.DATE_BORN));
        long date_come = getLong(getColumnIndex(   EmployeTable.Cols.DATE_COME));
        long date_out = getLong(getColumnIndex(    EmployeTable.Cols.DATE_OUT ));
        String address = getString(getColumnIndex( EmployeTable.Cols.ADDRESS  ));

        Employe employe = new Employe(UUID.fromString(uuid));
        employe.setId_items(UUID.fromString(id_items));
        employe.setName(name);
        employe.setJob(job);
        employe.setSex(sex);
        employe.setImage(image);
        employe.setNomer(nomer);
        employe.setEmail(email);
        employe.setDateBorn(new Date(date_born));
        employe.setDateCome(new Date(date_come));
        employe.setDateOut(new Date(date_out));
        employe.setAdress(address);

        return  employe;
    }

    Items getItem(){

        String uuid  = getString(getColumnIndex  (ItemsTable.Cols.ID_UUID));
        String item  = getString(getColumnIndex  (ItemsTable.Cols.ITEM));


        Items items = new Items();
        items.setId_UUID(UUID.fromString(uuid));
        items.setItem(item);
        return items;
    }

    Supplier getSupplier(){

        String uuid  = getString(   getColumnIndex(    SupplierTable.Cols.S_UUID      ));
        String id_items = getString(getColumnIndex(    SupplierTable.Cols.ID_ITEMS    ));
        String name = getString(    getColumnIndex(    SupplierTable.Cols.NAME        ));
        String organization = getString(getColumnIndex(SupplierTable.Cols.ORGANIZATION));
        String phone = getString(getColumnIndex(       SupplierTable.Cols.PHONE       ));
        String email = getString(getColumnIndex(       SupplierTable.Cols.EMAIL       ));
        String address = getString(getColumnIndex(     SupplierTable.Cols.ADDRESS     ));
        long date_come = getLong(getColumnIndex(       SupplierTable.Cols.DATE_COME   ));
        long date_out = getLong(getColumnIndex(        SupplierTable.Cols.DATE_OUT    ));
        long date_visit = getLong(getColumnIndex(      SupplierTable.Cols.VISIT_DATE  ));

        Supplier supplier = new Supplier(UUID.fromString(uuid));
        supplier.setId_items(UUID.fromString(id_items));
        supplier.setName(name);
        supplier.setOrganization(organization);
        supplier.setPhone(phone);
        supplier.setEmail(email);
        supplier.setAddress(address);
        supplier.setDateCome(new Date(date_come));
        supplier.setDateOut(new Date(date_out));
        supplier.setDateVisit(new Date(date_visit));
        return supplier;
    }
}
