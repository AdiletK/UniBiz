package com.example.unibiz.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.unibiz.Model.Category;
import com.example.unibiz.Model.Client;
import com.example.unibiz.Model.Employe;
import com.example.unibiz.Model.Items;
import com.example.unibiz.Model.Product;
import com.example.unibiz.Model.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.unibiz.DB.DBSchema.CategoryTable;
import static com.example.unibiz.DB.DBSchema.ClientTable;
import static com.example.unibiz.DB.DBSchema.EmployeTable;
import static com.example.unibiz.DB.DBSchema.ItemsTable;
import static com.example.unibiz.DB.DBSchema.ProductTable;
import static com.example.unibiz.DB.DBSchema.SupplierTable;

public class DatabaseOperation {
    private static  DatabaseOperation sOperation;

    private SQLiteDatabase mDatabase;

    private DatabaseOperation(Context context) {
        Context context1 = context.getApplicationContext();
        mDatabase = new CreationDataBase(context1)
                .getWritableDatabase();
    }

    public static DatabaseOperation get(Context context){
        if (sOperation==null){
            sOperation = new DatabaseOperation(context);
        }
        return sOperation;
    }

    //operations with Table Product
    public void add(Product product){
        ContentValues values = getValues(product);
        addOperation(values, ProductTable.NAME);
    }
    public void delete(Product product){
        deleteOperation(ProductTable.NAME,ProductTable.Cols.P_UUID,product.getId());
    }
    public void update(Product product){
        String uuid = product.getId().toString();
        ContentValues values = getValues(product);
        updateOperation(ProductTable.NAME,ProductTable.Cols.P_UUID,uuid, values);
    }
    public List<Product>getProducts(){
        List<Product> products = new ArrayList<>();
        try (CursorWrapperHelper cursor = query(ProductTable.NAME,
                null, null,null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                products.add(cursor.getProduct());
                cursor.moveToNext();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<Product>getProducts(String d1,String d2){
        List<Product> products = new ArrayList<>();
        try (CursorWrapperHelper cursor = query_by_date(ProductTable.NAME,ProductTable.Cols.DATE,
                d1,d2)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                products.add(cursor.getProduct());
                cursor.moveToNext();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public Product getProduct(UUID uuid){
        try (CursorWrapperHelper product = query(ProductTable.NAME,
                ProductTable.Cols.P_UUID + "=?",
                new String[]{uuid.toString()},null
        )) {
            if (product.getCount() == 0) {
                return null;
            }
            product.moveToFirst();
            return product.getProduct();
        }
    }

    //operation with Table Client
    public void add(Client client){
        ContentValues values = getValues(client);
        addOperation(values, ClientTable.NAME);
    }
    public void delete(Client client){
        deleteOperation(ClientTable.NAME,ClientTable.Cols.C_UUID,client.getId());
    }
    public void update(Client client){
        String uuid = client.getId().toString();
        ContentValues values = getValues(client);
        updateOperation(ClientTable.NAME,ClientTable.Cols.C_UUID,uuid,values);
    }
    public List<Client>getClients(){
        List<Client>clients = new ArrayList<>();
        try (CursorWrapperHelper cursor = query(ClientTable.NAME,
                null, null,ClientTable.Cols.VISIT_DATE)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                clients.add(cursor.getClient());
                cursor.moveToNext();
            }
        }
        return clients;
    }
    public List<Client>getClients(String d1,String d2){
        List<Client>clients = new ArrayList<>();
        try (CursorWrapperHelper cursor = query_by_date(ClientTable.NAME,ClientTable.Cols.VISIT_DATE,
                d1,d2)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                clients.add(cursor.getClient());
                cursor.moveToNext();
            }
        }
        return clients;

    }

    public Client getClient(UUID uuid){
        try(CursorWrapperHelper client = query(ClientTable.NAME,
                ClientTable.Cols.C_UUID + "=?",
                new String[]{uuid.toString()},null)){
            if (client.getCount()==0){
                return null;
            }
            client.moveToFirst();
            return client.getClient();
        }

    }

    //operation with Table Items
    public void add(Items items){
        ContentValues values = getValues(items);
        addOperation(values,ItemsTable.NAME);
    }
    public void delete(Items items){
        deleteOperation(ItemsTable.NAME,ItemsTable.Cols.ID_UUID,items.getId_UUID());
    }
    public void update(Items items){
        String id = items.getId_UUID().toString();
        ContentValues values = getValues(items);
        updateOperation(ItemsTable.NAME,ItemsTable.Cols.ID_UUID,id,values);
    }
    public List<Items>getItems(){
        List<Items>items = new ArrayList<>();
        try(CursorWrapperHelper cursor = query(ItemsTable.NAME,
                null,null,null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                items.add(cursor.getItem());
                cursor.moveToNext();
            }
        }
        return items;
    }
    public Items getItem(UUID uuid){
        try(CursorWrapperHelper item = query(ItemsTable.NAME,
                ItemsTable.Cols.ID_UUID + "=?",
                new String[]{uuid.toString()},null)) {
            if (item.getCount()==0){
                return null;
            }
            item.moveToFirst();
            return item.getItem();
        }
    }

    //operation with Table Employe
    public  void add(Employe employe){
        ContentValues values = getValues(employe);
        addOperation(values,EmployeTable.NAME);
    }
    public void delete(Employe employe){
        deleteOperation(EmployeTable.NAME,EmployeTable.Cols.E_UUID,employe.getUUID());
    }
    public void update(Employe employe){
        String id = employe.getUUID().toString();
        ContentValues values = getValues(employe);
        updateOperation(EmployeTable.NAME,EmployeTable.Cols.E_UUID,id,values);
    }
    public List<Employe>getEmployes(){
        List<Employe>employes = new ArrayList<>();
        try(CursorWrapperHelper cursor = query(EmployeTable.NAME,
                null,null,null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                employes.add(cursor.getEmploye());
                cursor.moveToNext();
            }
        }
        return employes;
    }
    public Employe getEmploye(UUID uuid){
        try(CursorWrapperHelper employe = query(EmployeTable.NAME,
                EmployeTable.Cols.E_UUID + "=?",
                new String[]{uuid.toString()},null)) {
            if (employe.getCount()==0){
                return null;
            }
            employe.moveToFirst();
            return employe.getEmploye();
        }
    }
    public Employe getEmploye(String name){
        try(CursorWrapperHelper employe = query(EmployeTable.NAME,
                EmployeTable.Cols.NAME + "=?",
                new String[]{name},null)) {
            if (employe.getCount()==0){
                return null;
            }
            employe.moveToFirst();
            return employe.getEmploye();
        }
    }

    //operation with Table Category
    public void add(Category category){
        ContentValues values = getValues(category);
        addOperation(values,CategoryTable.NAME);
    }
    public void delete(Category category){
        deleteOperation(CategoryTable.NAME,CategoryTable.Cols.C_UUID,
                category.getId());
    }
    public void update(Category category){
        String id = category.getId().toString();
        ContentValues values = getValues(category);
        updateOperation(CategoryTable.NAME,CategoryTable.Cols.C_UUID,id,values);
    }
    public List<Category>getCategories(){
        List<Category>categories = new ArrayList<>();
        try(CursorWrapperHelper cursor = query(CategoryTable.NAME,
                null,null,null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                categories.add(cursor.getCategory());
                cursor.moveToNext();
            }
        }
        return categories;
    }
    public Category getCategory(UUID uuid){
        try(CursorWrapperHelper category = query(CategoryTable.NAME,
                CategoryTable.Cols.C_UUID + "=?",
                new String[]{uuid.toString()},null)) {
            if (category.getCount()==0){
                return null;
            }
            category.moveToFirst();
            return category.getCategory();
        }
    }
    public Category getCategory(String name){
        try(CursorWrapperHelper category = query(CategoryTable.NAME,
                CategoryTable.Cols.NAME + "=?",
                new String[]{name},null)) {
            if (category.getCount()==0){
                return null;
            }
            category.moveToFirst();
            return category.getCategory();
        }
    }

    //operation with Table Supplier
    public void add(Supplier supplier){
        ContentValues values = getValues(supplier);
        addOperation(values,SupplierTable.NAME);
    }
    public void delete(Supplier supplier){
        deleteOperation(SupplierTable.NAME,SupplierTable.Cols.S_UUID,
                supplier.getUUID());
    }
    public void update(Supplier supplier){
        String id = supplier.getUUID().toString();
        ContentValues values = getValues(supplier);
        updateOperation(SupplierTable.NAME,SupplierTable.Cols.S_UUID,id,values);
    }
    public List<Supplier>getSuppliers(){
        List<Supplier>suppliers = new ArrayList<>();
        try(CursorWrapperHelper cursor = query(SupplierTable.NAME,
                null,null,null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                suppliers.add(cursor.getSupplier());
                cursor.moveToNext();
            }
        }
        return suppliers;
    }
    public Supplier getSupplier(UUID uuid){
        try(CursorWrapperHelper supplier = query(SupplierTable.NAME,
                SupplierTable.Cols.S_UUID + "=?",
                new String[]{uuid.toString()},null)) {
            if (supplier.getCount()==0){
                return null;
            }
            supplier.moveToFirst();
            return supplier.getSupplier();
        }
    }

    //tools
    private static ContentValues getValues(Client client){
        ContentValues values = new ContentValues();
        values.put(ClientTable.Cols.C_UUID    ,client.getId().toString());
        values.put(ClientTable.Cols.ID_EMPL   ,client.getId_empl().toString());
        values.put(ClientTable.Cols.ID_CAT    ,client.getId_category().toString());
        values.put(ClientTable.Cols.ID_ITEMS  ,client.getId_item().toString());
        values.put(ClientTable.Cols.NAME      ,client.getName());
        values.put(ClientTable.Cols.MODEL     ,client.getModel());
        values.put(ClientTable.Cols.NOMER     ,client.getNomer());
        values.put(ClientTable.Cols.IMEI      ,client.getImei());
        values.put(ClientTable.Cols.DATE_BORN ,client.getDate().getTime());
        values.put(ClientTable.Cols.VISIT_DATE,client.getVisitDate());
        values.put(ClientTable.Cols.PRICE     ,client.getPrice());
        return values;
    }
    private static ContentValues getValues(Product product){
        ContentValues values = new ContentValues();
        values.put(ProductTable.Cols.P_UUID   ,product.getId().toString());
        values.put(ProductTable.Cols.ID_EMPL  ,product.getId_empl().toString());
        values.put(ProductTable.Cols.ID_SUPL  ,product.getId_supl().toString());
        values.put(ProductTable.Cols.ID_ITEMS ,product.getId_items().toString());
        values.put(ProductTable.Cols.NAME     ,product.getName());
        values.put(ProductTable.Cols.IMEI_CODE,product.getImei_code());
        values.put(ProductTable.Cols.COUNT    ,product.getCount());
        values.put(ProductTable.Cols.PRICE    ,product.getPrice());
        values.put(ProductTable.Cols.DATE     ,product.getDate());
        return values;
    }
    private static ContentValues getValues(Employe employe){
        ContentValues values = new ContentValues();
        values.put(EmployeTable.Cols.E_UUID   ,employe.getUUID().toString());
        values.put(EmployeTable.Cols.ID_ITEMS ,employe.getId_items().toString());
        values.put(EmployeTable.Cols.NAME     ,employe.getName());
        values.put(EmployeTable.Cols.JOB      ,employe.getJob());
        values.put(EmployeTable.Cols.SEX      ,employe.getSex());
        values.put(EmployeTable.Cols.IMAGE    ,employe.getImage());
        values.put(EmployeTable.Cols.NOMER    ,employe.getNomer());
        values.put(EmployeTable.Cols.EMAIL    ,employe.getEmail());
        values.put(EmployeTable.Cols.DATE_BORN,employe.getDateBorn().getTime());
        values.put(EmployeTable.Cols.DATE_COME,employe.getDateCome().getTime());
        values.put(EmployeTable.Cols.DATE_OUT ,employe.getDateOut().getTime());
        values.put(EmployeTable.Cols.ADDRESS  ,employe.getAdress());

        return values;
    }
    private static ContentValues getValues(Category category){
        ContentValues values = new ContentValues();
        values.put(CategoryTable.Cols.C_UUID,category.getId().toString());
        values.put(CategoryTable.Cols.NAME  ,category.getName());
        values.put(CategoryTable.Cols.IMAGE ,category.getImage());
        values.put(CategoryTable.Cols.PRICE ,category.getPrice());
        values.put(CategoryTable.Cols.DATE  ,category.getDate().getTime());
        return values;
    }
    private static ContentValues getValues(Supplier supplier){
        ContentValues values = new ContentValues();
        values.put(SupplierTable.Cols.S_UUID      ,supplier.getUUID().toString());
        values.put(SupplierTable.Cols.ID_ITEMS    ,supplier.getId_items().toString());
        values.put(SupplierTable.Cols.NAME        ,supplier.getName());
        values.put(SupplierTable.Cols.ORGANIZATION,supplier.getOrganization());
        values.put(SupplierTable.Cols.PHONE       ,supplier.getPhone());
        values.put(SupplierTable.Cols.EMAIL       ,supplier.getEmail());
        values.put(SupplierTable.Cols.ADDRESS     ,supplier.getAddress());
        values.put(SupplierTable.Cols.DATE_COME   ,supplier.getDateCome().getTime());
        values.put(SupplierTable.Cols.DATE_OUT    ,supplier.getDateOut().getTime());
        values.put(SupplierTable.Cols.VISIT_DATE  ,supplier.getDateVisit().getTime());

        return values;
    }
    private static ContentValues getValues(Items items){
        ContentValues values = new ContentValues();
        values.put(ItemsTable.Cols.ID_UUID,items.getId_UUID().toString());
        values.put(ItemsTable.Cols.ITEM,items.getItem());

        return values;
    }

    private CursorWrapperHelper query(String table, String whereClause,
                                           String[] whereArgs,String orderby){
        Cursor cursor = mDatabase.query(
                table,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                orderby+" DESC"
        );
        return new CursorWrapperHelper(cursor);
    }
    private CursorWrapperHelper query_by_date(String tableName,String where, String date1, String date2){
        Cursor cursor = mDatabase.query(
                tableName,
                null,
                 where +" BETWEEN '" + date1 +"' AND '"+ date2+"'",
                null,
                null,
                null,
                where+" DESC"
        );
        return new CursorWrapperHelper(cursor);
    }


    private void deleteOperation(String table,String whereClause, UUID id) {
        mDatabase.delete(table, whereClause + "=?",
                new String[]{id.toString()});
    }
    private void addOperation(ContentValues values, String name) {
        mDatabase.insert(name, null, values);
    }
    private void updateOperation(String table,String whereClause,String uuid, ContentValues values) {
        mDatabase.update(table,values,
                whereClause + "=?",
                new String[]{uuid});
    }



}
