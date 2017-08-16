package com.example.gurjeet.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.gurjeet.inventoryapp.data.InventoryContract.StockEntry;
import com.example.gurjeet.inventoryapp.data.InventoryHelper;
import com.example.gurjeet.inventoryapp.data.InventoryItem;

public class CatalogActivity extends AppCompatActivity {

    private final static String LOG_TAG = CatalogActivity.class.getCanonicalName();
    InventoryHelper dbHelper;
    InventoryAdapter adapter;
    int lastVisibleItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        dbHelper = new InventoryHelper(this);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        final ListView listView = (ListView) findViewById(R.id.list_view);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        Cursor cursor = dbHelper.readStock();

        adapter = new InventoryAdapter(this, cursor);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == 0) return;
                final int currentFirstVisibleItem = view.getFirstVisiblePosition();
                if (currentFirstVisibleItem > lastVisibleItem) {
                    fab.show();
                } else if (currentFirstVisibleItem < lastVisibleItem) {
                    fab.hide();
                }
                lastVisibleItem = currentFirstVisibleItem;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.swapCursor(dbHelper.readStock());
    }

    public void clickOnViewItem(long id) {
        Intent intent = new Intent(this, EditorActivity.class);
        intent.putExtra("itemId", id);
        startActivity(intent);
    }

    public void clickOnSale(long id, int quantity) {
        dbHelper.sellOneItem(id, quantity);
        adapter.swapCursor(dbHelper.readStock());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_dummy_data:
                // add dummy data for testing
                addDummyData();
                adapter.swapCursor(dbHelper.readStock());
                break;
            case R.id.action_delete_all:
                deleteAllRowsFromTable();
                adapter.swapCursor(dbHelper.readStock());

        }
        return super.onOptionsItemSelected(item);
    }
    private int deleteAllRowsFromTable() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        return database.delete(StockEntry.TABLE_NAME, null, null);
    }

    /**
     * Add data for demo purposes
     */
    private void addDummyData() {
        InventoryItem maggi = new InventoryItem(
                "Maggi",
                "KDH",
                "20",
                4,
                "android.resource://com.example.gurjeet.inventoryapp/drawable/maggi");
        dbHelper.insertItem(maggi);

        InventoryItem nutella = new InventoryItem(
                "Nutella",
                "Jai Shoppe",
                "350",
                2,
                "android.resource://com.example.gurjeet.inventoryapp/drawable/nutella");
        dbHelper.insertItem(nutella);
    }
}
