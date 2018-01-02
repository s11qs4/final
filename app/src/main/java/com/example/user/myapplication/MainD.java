package com.example.user.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainD extends AppCompatActivity {
    ListView listd;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_d);
        listd = (ListView) findViewById(R.id.listd);

        MaindDb ae = new MaindDb(this);
        db = ae.getWritableDatabase();


        String[] colum = {"title", "object", "month", "price", "state"};
        Cursor c;
        c = db.query("main01", colum, null, null, null, null, null);

        MainD.Data[] data = new MainD.Data[c.getCount()];
        if (c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                data[i] = new MainD.Data();
                data[i].title = "店名:" + c.getString(0)+"物品"+c.getString(1);
                data[i].money = "數量:" + c.getString(2)+"價格:" + c.getString(3);
                data[i].describe = "狀態:" + c.getString(4);
                c.moveToNext();
            }
        }
        AdapterD a = new AdapterD(data, R.layout.listd);
        listd.setAdapter(a);

    }

    public class AdapterD extends BaseAdapter {
        private MainD.Data[] data;
        private int view;

        public AdapterD(MainD.Data[] data, int view) {
            this.data = data;
            this.view = view;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View rowview, ViewGroup parent) {
            rowview = getLayoutInflater().inflate(view, parent, false);
            TextView name = (TextView) rowview.findViewById(R.id.d1);
            TextView phone = (TextView) rowview.findViewById(R.id.d2);
            TextView address = (TextView) rowview.findViewById(R.id.d3);
            name.setText(data[position].title);
            phone.setText(data[position].money);
            address.setText(data[position].describe);
            return rowview;
        }

    }
    class Data{
        String title,money,describe;
    }
}
