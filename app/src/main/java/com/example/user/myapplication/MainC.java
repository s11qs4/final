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

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.TimeZone;

public class MainC extends AppCompatActivity {
    Button aq;
    TextView txt;
    ListView list1,list2;
    SQLiteDatabase db;
    SQLiteDatabase dbrw;
    SQLiteDatabase dbf;
    String name;

    double sum=0;
    int o;
    String[] b1=new String[40];
    String[] b2=new String[40];

    String[] w1=new String[40];
    String[] w2=new String[40];
    String[] w3=new String[40];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_c);
        txt=(TextView)findViewById(R.id.txtc);
        aq=(Button)findViewById(R.id.aq);
        list1=(ListView)findViewById(R.id.listc);

        list2=(ListView)findViewById(R.id.listdown);


        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        name=bundle.getString("NAME");
        txt.setText("店名:"+name);

        MainbDB h=new MainbDB(this);
        db=h.getWritableDatabase();

        MaincDB aw=new MaincDB(this);
        dbrw=aw.getWritableDatabase();
        MaindDb ae=new MaindDb(this);
        dbf=ae.getWritableDatabase();





        String[] colum={"title","object","price","describe"};
        Cursor c;
        c=db.query("main01",colum,"title='"+name+"' ",null,null,null,null);

        MainC.Data[] data=new MainC.Data[c.getCount()];
        if(c.getCount()>0){
            c.moveToFirst();
            for (int i=0;i<c.getCount();i++){
                data[i]=new MainC.Data();
                b1[i]=c.getString(1);
                b2[i]=c.getString(2);
                data[i].title="物品:"+c.getString(1)+"\n";
                data[i].money="價格:"+c.getString(2)+"\n";
                data[i].describe="描述:"+c.getString(3)+"\n";
                c.moveToNext();
            }
        }
        MainC.AdapterC a=new MainC.AdapterC(data,R.layout.listb);
        list1.setAdapter(a);

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final String[] list={"0","1","2","3","4","5","6","7","8","9","取消"};
                final int b=position;
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainC.this);
                dialog.setTitle("下單數量");
                dialog.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if(which>0&&which<10)
                        {
                            o=which;

                            sum=Double.parseDouble(b2[b]);
                            sum=sum*o;
                            ContentValues cv=new ContentValues();
                            ContentValues co=new ContentValues();
                            cv.put("title",name);
                            cv.put("object",b1[b]);
                            cv.put("month",o);
                            cv.put("price",Double.toString(sum));

                            co.put("title",name);
                            co.put("object",b1[b]);
                            co.put("month",o);
                            co.put("price",Double.toString(sum));
                            co.put("state","未退貨");
                            dbrw.insert("main01", null,cv);

                            dbf.insert("main01", null,co);

                        }
                        else{
                            o=0;
                        }



                    }
                });
                dialog.show();
            }
        });



        aq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cc();
            }
        });



    }
    public void cc(){
        String[] colums={"title","object","month","price"};
        Cursor cs;
        cs=dbrw.query("main01",colums,"title='"+name+"' ",null,null,null,null);
        MainC.Data[] dat=new MainC.Data[cs.getCount()];
        if(cs.getCount()>0){
            cs.moveToFirst();
            for (int i=0;i<cs.getCount();i++){
                dat[i]=new MainC.Data();
                w1[i]=cs.getString(1);
                w2[i]=cs.getString(2);
                w3[i]=cs.getString(3);
                dat[i].title="物品:"+cs.getString(1)+"\n";
                dat[i].money="數量:"+cs.getString(2)+"\n";
                dat[i].describe="價格:"+cs.getString(3)+"\n";
                cs.moveToNext();
            }
        }
        AdapterC bbb=new AdapterC(dat,R.layout.listb);
        list2.setAdapter(bbb);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainC.this);
                dialog.setTitle("請選擇功能");
                dialog.setNegativeButton("退貨", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dbrw.delete("main01","title='"+name+"' "+" AND "+"object='"+w1[position]+"'"+" AND "+"month='"+w2[position]+
                                "'"+" AND "+"price='"+w3[position]+"'",null);

                        dbf.delete("main01","title='"+name+"' "+" AND "+"object='"+w1[position]+"'"+" AND "+"month='"+w2[position]+
                                "'"+" AND "+"price='"+w3[position]+"'",null);

                        ContentValues co=new ContentValues();

                        co.put("title",name);
                        co.put("object",w1[position]);
                        co.put("month",w2[position]);
                        co.put("price",w3[position]);
                        co.put("state","退貨了");
                        dbf.insert("main01", null,co);

                    }
                });
                dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }


    public class AdapterC extends BaseAdapter {
        private MainC.Data[] data;
        private int view;

        public AdapterC(MainC.Data[] data, int view){
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
            TextView name=(TextView)rowview.findViewById(R.id.nameb);
            TextView phone=(TextView)rowview.findViewById(R.id.moneyb);
            TextView address=(TextView)rowview.findViewById(R.id.describeb);
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

