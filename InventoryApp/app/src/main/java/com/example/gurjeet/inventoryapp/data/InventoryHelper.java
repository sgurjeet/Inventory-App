package com.example.gurjeet.inventoryapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gurjeet.inventoryapp.data.InventoryContract.StockEntry;

/**
 * Created by Gurjeet on 06-Mar-17.
 */

public class InventoryHelper extends SQLiteOpenHelper{

        public final static String DB_NAME = "inventory.db";
        public final static int DB_VERSION = 1;
        public final static String LOG_TAG = InventoryHelper.class.getCanonicalName();

        public InventoryHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(StockEntry.CREATE_TABLE_STOCK);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public void insertItem(InventoryItem item) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(StockEntry.COLUMN_NAME, item.getProductName());
            values.put(StockEntry.COLUMN_SNAME, item.getSupname());
            values.put(StockEntry.COLUMN_PRICE, item.getPrice());
            values.put(StockEntry.COLUMN_QUANTITY, item.getQuantity());
            values.put(StockEntry.COLUMN_IMAGE, item.getImage());
            long id = db.insert(StockEntry.TABLE_NAME, null, values);
        }

        public Cursor readStock() {
            SQLiteDatabase db = getReadableDatabase();
            String[] projection = {
                    StockEntry._ID,
                    StockEntry.COLUMN_NAME,
                    StockEntry.COLUMN_SNAME,
                    StockEntry.COLUMN_PRICE,
                    StockEntry.COLUMN_QUANTITY,
                    StockEntry.COLUMN_IMAGE
            };
            Cursor cursor = db.query(
                    StockEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            return cursor;
        }

        public Cursor readItem(long itemId) {
            SQLiteDatabase db = getReadableDatabase();
            String[] projection = {
                    StockEntry._ID,
                    StockEntry.COLUMN_NAME,
                    StockEntry.COLUMN_SNAME,
                    StockEntry.COLUMN_PRICE,
                    StockEntry.COLUMN_QUANTITY,
                    StockEntry.COLUMN_IMAGE
            };
            String selection = StockEntry._ID + "=?";
            String[] selectionArgs = new String[] { String.valueOf(itemId) };

            Cursor cursor = db.query(
                    StockEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            return cursor;
        }

        public void updateItem(long currentItemId, int quantity) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(StockEntry.COLUMN_QUANTITY, quantity);
            String selection = StockEntry._ID + "=?";
            String[] selectionArgs = new String[] { String.valueOf(currentItemId) };
            db.update(StockEntry.TABLE_NAME,
                    values, selection, selectionArgs);
        }

        public void sellOneItem(long itemId, int quantity) {
            SQLiteDatabase db = getWritableDatabase();
            int newQuantity = 0;
            if (quantity > 0) {
                newQuantity = quantity -1;
            }
            ContentValues values = new ContentValues();
            values.put(StockEntry.COLUMN_QUANTITY, newQuantity);
            String selection = StockEntry._ID + "=?";
            String[] selectionArgs = new String[] { String.valueOf(itemId) };
            db.update(StockEntry.TABLE_NAME,
                    values, selection, selectionArgs);
        }
}
