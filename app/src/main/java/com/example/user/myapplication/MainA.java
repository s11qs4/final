package com.example.user.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainA extends AppCompatActivity {
    String name;
    TextView titude;
    private String add;
    double Latitude,Longtitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        titude=(TextView)findViewById(R.id.titude);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        name=bundle.getString("NAME");
        add=bundle.getString("address");

        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (add.length()>0)
                {
                    Geocoder geocoder=new Geocoder(MainA.this);
                    List<Address> addresses=null;
                    Address address=null;
                    try {
                        addresses=geocoder.getFromLocationName(add,1);
                    }catch (IOException e)
                    {
                        Log.e("MainA",e.toString());
                    }
                    if (addresses==null||addresses.isEmpty()){
                        Toast.makeText(MainA.this,"找不到此地址",Toast.LENGTH_SHORT).show();
                        titude.setText("找不到"+add+"這個位置");
                    }else{
                        address=addresses.get(0);
                        Latitude=address.getLatitude();
                        Longtitude=address.getLongitude();

                        if(ActivityCompat.checkSelfPermission(MainA.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(MainA.this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                            return;
                        }
                        googleMap.setMyLocationEnabled(true);
                        MarkerOptions ml=new MarkerOptions();
                        ml.position(new LatLng(Latitude,Longtitude));
                        ml.title(name);
                        ml.draggable(true);
                        googleMap.addMarker(ml);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude,Longtitude),16));

                        titude.setText("位址:"+add+"\n"+"經度="+Latitude+"緯度="+Longtitude);
                    }

                }

            }
        });




    }
}
