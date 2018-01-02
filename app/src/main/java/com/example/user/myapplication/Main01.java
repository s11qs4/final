package com.example.user.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Main01 extends AppCompatActivity {
    EditText editname,editphone,editaddress;
    Button add,edit,delete,query;
    SQLiteDatabase dbrw,o1db;
    String[] b=new String[40];
    String[] b1=new String[40];
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main01);
        list=(ListView)findViewById(R.id.list);

        editname=(EditText)findViewById(R.id.editText);
        editphone=(EditText)findViewById(R.id.editText2);
        editaddress=(EditText)findViewById(R.id.editText3);


        add=(Button)findViewById(R.id.add);
        edit=(Button)findViewById(R.id.edit);
        delete=(Button)findViewById(R.id.move);
        query=(Button)findViewById(R.id.query);

        MyDBHelper dbhelper=new MyDBHelper(this);
        dbrw=dbhelper.getWritableDatabase();

        o1db db=new o1db(this);
        o1db=db.getWritableDatabase();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBook();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renewBook();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryBook();
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final String[] list={"A:在MAP上標記位置","B:商品目錄管理","C:下單管理","D:歷史銷售紀錄","取消"};
                AlertDialog.Builder dialog=new AlertDialog.Builder(Main01.this);
                dialog.setTitle("功能表單");
                dialog.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent i=new Intent();
                        Bundle bundle = new Bundle();
                        switch (which){
                            case 0:
                                i.setClass(Main01.this,MainA.class);
                                bundle.putString("NAME", b[position]);
                                bundle.putString("address", b1[position]);
                                i.putExtras(bundle);
                                startActivity(i);
                                break;
                            case 1:
                                i.setClass(Main01.this,MainB.class);
                                bundle.putString("NAME", b[position]);
                                i.putExtras(bundle);
                                startActivity(i);
                                break;
                            case 2:
                                i.setClass(Main01.this,MainC.class);
                                bundle = new Bundle();
                                bundle.putString("NAME", b[position]);
                                i.putExtras(bundle);

                                startActivity(i);
                                break;
                            case 3:
                                i.setClass(Main01.this,MainD.class);
                                startActivity(i);
                                break;
                        }
                    }
                });


                dialog.show();
            }
        });



    }
    public void newBook(){
        if(editname.getText().toString().equals("")||editphone.getText().toString().equals("")||editaddress.getText().toString().equals(""))
            Toast.makeText(this,"輸入資料不完全",Toast.LENGTH_SHORT).show();
        else {
            ContentValues cv=new ContentValues();
            cv.put("title",editname.getText().toString());
            cv.put("phone",editphone.getText().toString());
            cv.put("address",editaddress.getText().toString());
            dbrw.insert("main01", null,cv);


            ContentValues cv1=new ContentValues();
            cv1.put("title",editname.getText().toString());
            cv1.put("address",editaddress.getText().toString());
            cv1.put("star","0");
            o1db.insert("main01", null,cv1);



            Toast.makeText(this,"新增店名:"+editname.getText().toString()+"電話:"+editphone.getText().toString()+"地址:"+editaddress.getText().toString(),Toast.LENGTH_SHORT).show();
            editname.setText("");
            editphone.setText("");
            editaddress.setText("");
        }
    }
    public void renewBook(){
        if(editname.getText().toString().equals("")||editphone.getText().toString().equals("")||editaddress.getText().toString().equals(""))
            Toast.makeText(this,"沒有輸入更新值",Toast.LENGTH_SHORT).show();
        else {

            ContentValues cv=new ContentValues();
            cv.put("phone",editphone.getText().toString());
            cv.put("address",editaddress.getText().toString());
            dbrw.update("main01",cv,"title='"+editname.getText().toString()+"'",null);

            ContentValues cv1=new ContentValues();
            cv1.put("address",editaddress.getText().toString());
            cv1.put("star","0");
            o1db.update("main01",cv1,"title='"+editname.getText().toString()+"'",null);




            Toast.makeText(this,"成功",Toast.LENGTH_SHORT).show();
            editname.setText("");
            editphone.setText("");
            editaddress.setText("");
        }
    }
    public void deleteBook(){
        if(editname.getText().toString().equals(""))
            Toast.makeText(this,"請輸入要刪除之值",Toast.LENGTH_SHORT).show();
        else {
            dbrw.delete("main01","title="+"'"+editname.getText().toString()+"'",null);
            o1db.delete("main01","title="+"'"+editname.getText().toString()+"'",null);
            Toast.makeText(this,"刪除成功",Toast.LENGTH_SHORT).show();
            editname.setText("");
            editphone.setText("");
            editaddress.setText("");
        }
    }
    public void queryBook(){
        String[] colum={"title","phone","address"};
        Cursor c;
        if(editname.getText().toString().equals(""))
            c=dbrw.query("main01",colum,null,null,null,null,null);
        else {
            c=dbrw.query("main01",colum,"title="+"'"+editname.getText().toString()+"'",null,null,null,null);
        }

        Data[] data=new Data[c.getCount()];



        if(c.getCount()>0){
            c.moveToFirst();
            for (int i=0;i<c.getCount();i++){
                data[i]=new Data();
                b[i]=c.getString(0);
                b1[i]=c.getString(2);
                data[i].title="店名:"+c.getString(0)+"\n";
                data[i].phone="電話:"+c.getString(1)+"\n";
                data[i].address="地址:"+c.getString(2)+"\n";
                c.moveToNext();
            }
        }
      Adapter a=new Adapter(data,R.layout.list);
        list.setAdapter(a);

        Toast.makeText(this,"共有"+c.getCount()+"筆記錄",Toast.LENGTH_SHORT).show();

    }


    public class Adapter extends BaseAdapter{
        private Data[] data;
        private int view;

        public Adapter(Data[] data,int view){
            this.data=data;
            this.view=view;
        }

        @Override
        public int getCount() { return data.length;}
        @Override
        public Object getItem(int position) { return data[position];}
        @Override
        public long getItemId(int position) {return 0;}

        @Override
        public View getView(int position, View rowview, ViewGroup parent) {
            rowview=getLayoutInflater().inflate(view,parent,false);
            TextView name=(TextView)rowview.findViewById(R.id.name);
            TextView phone=(TextView)rowview.findViewById(R.id.phone);
            TextView address=(TextView)rowview.findViewById(R.id.address);
            name.setText(data[position].title);
            phone.setText(data[position].phone);
            address.setText(data[position].address);
            return rowview;
        }

    }

class Data{
    String title,phone,address;
}

}

