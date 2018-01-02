package com.example.user.myapplication;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class o1 extends AppCompatActivity {
    SQLiteDatabase db;
    Button query;
    String y;
    int ooo;
    String[] b=new String[40];
    String[] b1=new String[40];
    ListView o1list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o1);
        o1db dbhelper=new o1db(this);
        db=dbhelper.getWritableDatabase();

        query=(Button)findViewById(R.id.o1query);
        o1list=(ListView)findViewById(R.id.star);

        o1list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                ooo=position;
                final String[] list={"1星","2星","3星","4星","5星","取消"};
                AlertDialog.Builder dialog=new AlertDialog.Builder(o1.this);
                dialog.setTitle("星數評論");
                dialog.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        switch (which){
                            case 0:
                                y="1";
                                break;
                            case 1:
                                y="2";
                                break;
                            case 2:
                                y="3";
                                break;
                            case 3:
                                y="4";
                                break;
                            case 4:
                                y="5";
                                break;
                            default:
                                break;

                        }
                        renewBook();
                    }
                });
                dialog.show();
            }

        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryBook();
            }

        });
    }

    public void renewBook(){
            ContentValues cv=new ContentValues();
            ContentValues cv1=new ContentValues();
            cv1.put("star",y);
             db.update("main01",cv1,"title='"+ b[ooo]+"'",null);

    }

    public void queryBook(){
        String[] colum={"title","address","star"};
        Cursor c;
        c=db.query("main01",colum,null,null,null,null,null);

        Data[] data=new Data[c.getCount()];



        if(c.getCount()>0){
            c.moveToFirst();
            for (int i=0;i<c.getCount();i++){
                data[i]=new Data();
                b[i]=c.getString(0);
                data[i].title="店名:"+c.getString(0)+"\n";
                data[i].phone="地址:"+c.getString(1)+"\n";
                data[i].address=c.getString(2)+"星"+"\n";
                c.moveToNext();

            }
        }
        Adapter a=new Adapter(data,R.layout.star);
        o1list.setAdapter(a);
        Toast.makeText(this,"共有"+c.getCount()+"筆記錄",Toast.LENGTH_SHORT).show();

    }

public class Adapter extends BaseAdapter {
    private o1.Data[] data;
    private int view;

    public Adapter(o1.Data[] data, int view){
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
        TextView name=(TextView)rowview.findViewById(R.id.s1);
        TextView phone=(TextView)rowview.findViewById(R.id.s2);
        TextView address=(TextView)rowview.findViewById(R.id.s3);
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
