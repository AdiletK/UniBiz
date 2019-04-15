package com.example.unibiz.DB;

import android.provider.BaseColumns;

 class DBSchema {

     static final class ProductTable {
        static final String NAME = "product";
          static final class Cols implements BaseColumns{
             static final String P_UUID = "uuid";
             static final String ID_EMPL = "id_empl";
             static final String ID_SUPL = "id_supl";
             static final String NAME = "name";
             static final String IMEI_CODE = "imei_code";
             static final String COUNT = "pr_count";
             static final String PRICE = "price";
             static final String ID_ITEMS = "id_item";
             static final String DATE = "pr_data";
        }
    }
     static final class EmployeTable{
        static final String NAME = "employe";
         static final class Cols implements BaseColumns{
            static final String E_UUID = "uuid";
            static final String NAME = "name";
            static final String JOB = "job";
            static final String SEX = "sex";
            static final String IMAGE = "image";
            static final String NOMER = "nomer";
            static final String EMAIL = "email";
            static final String DATE_BORN = "date_born";
            static final String DATE_COME = "date_come";
            static final String DATE_OUT= "date_out";
            static final String ADDRESS= "address";
            static final String ID_ITEMS= "id_items";
        }
    }

    static final class ClientTable{
        static final String NAME = "client";
        static final class Cols implements BaseColumns{
             static final String C_UUID = "uuid";
             static final String ID_CAT = "id_cat";
             static final String ID_EMPL = "id_empl";
             static final String MODEL = "model";
             static final String NOMER = "nomer";
             static final String IMEI = "imei";
             static final String DATE_BORN = "date_born";
             static final String VISIT_DATE = "visit_date";
             static final String NAME = "name";
             static final String PRICE= "price";
             static final String ID_ITEMS= "id_items";
        }
    }
     static final class SupplierTable{
        static final String NAME = "supplier";
         static final class Cols implements BaseColumns{
             static final String S_UUID = "uuid";
             static final String NAME = "name";
             static final String ORGANIZATION = "organization";
             static final String PHONE = "phone";
             static final String EMAIL = "email";
             static final String ADDRESS = "address";
             static final String DATE_COME = "date_come";
             static final String VISIT_DATE = "visit_date";
             static final String DATE_OUT = "date_out";
             static final String ID_ITEMS = "id_items";
        }
    }
     static final class ItemsTable{
        static final String NAME = "items";
         static final class Cols implements BaseColumns{
             static final String ID_UUID =  "id_uuid";
             static final String ITEM =  "item";

        }
    }

     static final class CategoryTable{
        static final String NAME = "category";
         static final class Cols implements BaseColumns{
             static final String C_UUID =  "uuid";
             static final String NAME =  "name";
             static final String IMAGE =  "image";
             static final String PRICE =  "price";
             static final String DATE =  "date_c";

        }
    }
}
