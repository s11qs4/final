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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainB extends AppCompatActivity {
    EditText title,price,object;
    Button add,edit,move,query;
    TextView txt;
    SQLiteDatabase db;
    String name;

    ListView list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_b);
        add=(Button)findViewById(R.id.addB);
        edit=(Button)findViewById(R.id.editB);
        move=(Button)findViewById(R.id.moveB);
        query=(Button)findViewById(R.id.queryB);
        txt=(TextView)findViewById(R.id.textView7);

        title=(EditText)findViewById(R.id.it);
        price=(EditText)findViewById(R.id.money);
        object=(EditText)findViewById(R.id.describe);

        list1=(ListView)findViewById(R.id.list1);

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        name=bundle.getString("NAME");
        txt.setText(name);


        MainbDB F=new MainbDB(this);
        db=F.getWritableDatabase();

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

        move.setOnClickListener(new View.OnClickListener() {
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






    }

    public void newBook(){
        if(title.getText().toString().equals("")||price.getText().toString().equals("")||object.getText().toString().equals(""))
            Toast.makeText(this,"輸入資料不完全",Toast.LENGTH_SHORT).show();
        else {
            ContentValues cv=new ContentValues();
            cv.put("title",name);
            cv.put("object",title.getText().toString());
            cv.put("price",price.getText().toString());
            cv.put("describe",object.getText().toString());
            db.insert("main01", null,cv);

            Toast.makeText(this,"新增商品:"+title.getText().toString()+"價格:"+price.getText().toString()+"描述:"+object.getText().toString(),Toast.LENGTH_SHORT).show();
            title.setText("");
            price.setText("");
            object.setText("");
        }
    }
    public void renewBook(){
        if(title.getText().toString().equals("")||price.getText().toString().equals("")||object.getText().toString().equals(""))
            Toast.makeText(this,"沒有輸入更新值",Toast.LENGTH_SHORT).show();
        else {

            ContentValues cv=new ContentValues();
            cv.put("price",price.getText().toString());
            cv.put("describe",object.getText().toString());

            db.update("main01",cv,"title='"+name+"'"+" AND "+"object='"+title.getText().toString()+"'",null);
            Toast.makeText(this,"成功",Toast.LENGTH_SHORT).show();
            title.setText("");
            price.setText("");
            object.setText("");
        }
    }
    public void deleteBook(){
        if(title.getText().toString().equals(""))
            Toast.makeText(this,"請輸入要刪除之值",Toast.LENGTH_SHORT).show();
        else {
            db.delete("main01","title='"+name+"' "+" AND "+"object='"+title.getText().toString()+"'",null);

            Toast.makeText(this,"刪除成功",Toast.LENGTH_SHORT).show();
            title.setText("");
            price.setText("");
            object.setText("");
        }
    }
    public void queryBook(){
        String[] colum={"title","object","price","describe"};
        Cursor c;
        if(title.getText().toString().equals(""))
            c=db.query("main01",colum,"title='"+name+"' ",null,null,null,null);
        else {
            c=db.query("main01",colum,"title='"+name+"' "+" AND "+"object='"+title.getText().toString()+"'",null,null,null,null);
        }

        MainB.Data[] data=new MainB.Data[c.getCount()];
        if(c.getCount()>0){
            c.moveToFirst();
            for (int i=0;i<c.getCount();i++){
                data[i]=new MainB.Data();

                data[i].title="物品:"+c.getString(1)+"\n";
                data[i].money="價格:"+c.getString(2)+"\n";
                data[i].describe="描述:"+c.getString(3)+"\n";
                c.moveToNext();
            }
        }
        MainB.AdapterB a=new MainB.AdapterB(data,R.layout.listb);
        list1.setAdapter(a);

        Toast.makeText(this,"共有"+c.getCount()+"筆記錄",Toast.LENGTH_SHORT).show();

    }


    public class AdapterB extends BaseAdapter {
        private MainB.Data[] data;
        private int view;

        public AdapterB(MainB.Data[] data, int view){
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

