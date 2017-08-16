package com.example.gurjeet.inventoryapp.data;

import android.provider.BaseColumns;

/**
 * Created by Gurjeet on 06-Mar-17.
 */

public class InventoryContract {
    public InventoryContract(){}
        public static final class StockEntry implements BaseColumns {

            public static final String TABLE_NAME = "stock";

            public static final String _ID = BaseColumns._ID;
            public static final String COLUMN_NAME = "name";
            public static final String COLUMN_SNAME = "supplier";
            public static final String COLUMN_PRICE = "price";
            public static final String COLUMN_QUANTITY = "quantity";
            public static final String COLUMN_IMAGE = "image";

            public static final String CREATE_TABLE_STOCK = "CREATE TABLE " +
                    StockEntry.TABLE_NAME + "(" +
                    StockEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    StockEntry.COLUMN_NAME + " TEXT NOT NULL," +
                    StockEntry.COLUMN_SNAME+ " TEXT NOT NULL," +
                    StockEntry.COLUMN_PRICE + " TEXT NOT NULL," +
                    StockEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0," +
                    StockEntry.COLUMN_IMAGE + " TEXT NOT NULL" + ");";
        }
}
