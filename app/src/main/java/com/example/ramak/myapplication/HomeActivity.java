package com.example.ramak.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;

import com.example.ramak.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by ramak on 10/19/2017.
 */

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback {

    private TabHost tabHost;

    private GoogleMap mMap;
    private Button log_out;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);



        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        LayoutInflater inflater = getLayoutInflater().from(this);
        inflater.inflate(R.layout.tab1, tabHost.getTabContentView());
        inflater.inflate(R.layout.tab2, tabHost.getTabContentView());
        inflater.inflate(R.layout.tab3, tabHost.getTabContentView());
        inflater.inflate(R.layout.tab4, tabHost.getTabContentView());

        tabHost.addTab(tabHost.newTabSpec("tab01")
                .setIndicator("", this.getResources().getDrawable(R.drawable.home))
                .setContent(R.id.linearLayout01));

        tabHost.addTab(tabHost.newTabSpec("tab02")
                .setIndicator("", this.getResources().getDrawable(R.drawable.search))
                .setContent(R.id.linearLayout02));

        tabHost.addTab(tabHost.newTabSpec("tab03")
                .setIndicator("", this.getResources().getDrawable(R.drawable.information))
                .setContent(R.id.linearLayout03));

        tabHost.addTab(tabHost.newTabSpec("tab04")
                .setIndicator("", this.getResources().getDrawable(R.drawable.setting))
                .setContent(R.id.linearLayout04));

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Corvallis, US, and move the camera.
        LatLng Corvallis = new LatLng(44.564568, -123.262047);
        mMap.addMarker(new MarkerOptions().position(Corvallis).title("Marker in Corvallis"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Corvallis));
    }
}
