package com.example.ramak.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapLocationActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,AdapterView.OnItemSelectedListener {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private String user;
    private String locations;
    private String userId;
    private String latitude;
    private String longitude;
    private String status;
    private Integer active;
    private Integer update;
    private Integer refresh_1 = 0;
    private Button refresh;
    private Button filter;
    private Spinner spinner;
    private String selectedMajor;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    //private BitmapDescriptorFactory colour;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        Bundle param=getIntent().getExtras();
        user=(String) param.get("user");
        Log.d("userID",user);
        locations=(String) param.get("locations");
        update=(Integer) param.get("update");
        refresh =(Button)findViewById(R.id.refresh);
        //filter =(Button)findViewById(R.id.filter);
        spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(this);
        List<String> list = new ArrayList<String>();
        list.add("ALL");
        list.add("CS");
        list.add("ECE");
        list.add("ME");
        list.add("ROB");
        list.add("CE");
        list.add("BA");
        list.add("IE");
        list.add("ST");
        list.add("NSE");
        list.add("MATS");
        list.add("PH");
        list.add("FIN");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        getSupportActionBar().setTitle("Map Location Activity");
        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                refresh_1 = 1;
                refreshLocations();
                //startActivity(new Intent(getApplicationContext(),MapLocationActivity.class).putExtra("user",user));
            }
        });

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }

        MapLocationActivity mapLocationActivity = new MapLocationActivity();

        // save to database current location
        //mapLocationActivity.updateUserLocation(String.valueOf(42),String.valueOf(89));
        ArrayList locale = new ArrayList();
        // load markers from database
        try {

            //funcions per a cridar el string amb JSON i convertir-lo de nou a JSON
            JSONArray jsas = new JSONArray(locations);
            Log.d("json",jsas.length()+"  ");
            ArrayList loc = new ArrayList();
            for (int i =0; i < jsas.length(); i++)
            {
                Integer j =0;

                JSONObject message = jsas.getJSONObject(i);
                Log.d("in for loop",message.toString());
                if (message.getString("title").equals("userId")){
                    userId = message.getString("value");
                    loc.add(userId);
                }
                if (message.getString("title").equals("latitude")){
                    latitude =  message.getString("value");
                    loc.add(latitude);
                }
                if (message.getString("title").equals("longitude")){
                    longitude = message.getString("value");
                    loc.add(longitude);
                }
                if (message.getString("title").equals("userActive")){
                    active = Integer.parseInt(message.getString("value"));
                    loc.add(active);
                }
                if (message.getString("title").equals("userStatus")){
                    status = message.getString("value");
                    loc.add(status);
                    j=1;
                }
                if (j ==1 && loc.size()!=0) {
                    Log.d("loc",loc.toString());
                    Log.d("lat",Double.parseDouble(loc.get(1).toString())+"  "+ Double.parseDouble(loc.get(2).toString()));
                    if (loc.get(4).toString().equals("green")){
                        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(loc.get(1).toString()), Double.parseDouble(loc.get(2).toString()))).title(loc.get(0).toString()).snippet(loc.get(3).toString())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        marker.setTag("green");
                    }
                    else if (loc.get(4).toString().equals("blue")){
                        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(loc.get(1).toString()), Double.parseDouble(loc.get(2).toString()))).title(loc.get(0).toString()).snippet(loc.get(3).toString())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        marker.setTag("blue");
                    }
                    else if (loc.get(4).toString().equals("yellow")){
                        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(loc.get(1).toString()), Double.parseDouble(loc.get(2).toString()))).title(loc.get(0).toString()).snippet(loc.get(3).toString())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                        marker.setTag("yellow");
                    }
                    else if (loc.get(4).toString().equals("red")){
                        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(loc.get(1).toString()), Double.parseDouble(loc.get(2).toString()))).title(loc.get(0).toString()).snippet(loc.get(3).toString())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        marker.setTag("red");
                    }
                    else {
                        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(loc.get(1).toString()), Double.parseDouble(loc.get(2).toString()))).title(loc.get(0).toString()).snippet(loc.get(3).toString())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        marker.setTag("orange");//...
                    }
                    loc.clear();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                /*// retrieve data based on marker title or snippet from database and send to profile view
                String title = marker.getTitle();
                Intent intent = new Intent();
                startActivity(new Intent(getApplicationContext(),ProfileViewActivity.class).putExtra("user",user).putExtra("title",title));
                return  true;*/
                String title = marker.getTitle();
                String tag = marker.getTag().toString();
                getUserDetails(title,user,tag);
                return true;
            }
        });
        /*mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String title = marker.getTitle();
                String tag = marker.getTag().toString();
                getUserDetails(title,user,tag);
                //Intent intent = new Intent();
                //startActivity(new Intent(getApplicationContext(),ProfileViewActivity.class).putExtra("user",user).putExtra("title",title));
            }
        });*/
        LatLng latLng = new LatLng(44.5648718, -123.2762719);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        MapLocationActivity mapLocationActivity = new MapLocationActivity();

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        // save to database current location
        if (update == 1 ) {
            mapLocationActivity.updateUserLocation(user,String.valueOf(location.getLatitude()), String.valueOf((location.getLongitude())));
            update =0;
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.snippet(latLng+"");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapLocationActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void updateUserLocation(String user,String latitude,String longitude) {


        HashMap<String, String> params = new HashMap<>();
        params.put("userId", user);
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        params.put("userActive", "1");
        params.put("userStatus", "violet");
        Log.d("updATE LOCATION",latitude);
        //Calling the create hero API
        MapLocationActivity.PerformNetworkRequest request = new MapLocationActivity.PerformNetworkRequest(Api.URL_UPDATEUSERLOCATION_USER, params, CODE_POST_REQUEST);
        request.execute();
    }

    public void getUserDetails(String user1,String user,String tag) {


        HashMap<String, String> params = new HashMap<>();
        params.put("userId", user1);
        params.put("user", user);
        params.put("tag", tag);
        //Calling the create hero API
        MapLocationActivity.PerformNetworkRequest request = new MapLocationActivity.PerformNetworkRequest(Api.URL_GETUSERDETAILS_USER, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void refreshLocations() {

        HashMap<String, String> params = new HashMap<>();
        params.put("userId", user);
        params.put("refresh_1", refresh_1.toString());
        //Calling the create hero API
        MapLocationActivity.PerformNetworkRequest request = new MapLocationActivity.PerformNetworkRequest(Api.URL_LISTLOCATIONS_USER, params, CODE_POST_REQUEST);
        request.execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedMajor = parent.getItemAtPosition(position).toString();
        if (selectedMajor.equals("ALL")){

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //inner class to perform network request extending an AsyncTask
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;
        GoogleMap rGoogleMap;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                Log.d("here1",object.toString());
                /*if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();


                    //refreshing the herolist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet
                    //refreshHeroList(object.getJSONArray("heroes"));
                }*/
                if (object.names().get(0).equals("success") && Integer.parseInt(params.get("refresh_1"))==1){
                    //Toast.makeText(getApplicationContext(),"SUCCESS", Toast.LENGTH_SHORT).show();
                    locations = object.getString("success");
                    mGoogleMap.clear();
                    onMapReady(mGoogleMap);
                    Log.d("output",object.getString("success"));
                    //startActivity(new Intent(getApplicationContext(),MapLocationActivity.class));

                    //startActivity(new Intent(getApplicationContext(),ProfileViewActivity.class).putExtra("user1",object.getString("success")).putExtra("user",params.get("user")));
                }
                if (object.names().get(0).equals("success")){
                    //Toast.makeText(getApplicationContext(),"SUCCESS", Toast.LENGTH_SHORT).show();
                    Log.d("output",object.getString("success"));
                    //startActivity(new Intent(getApplicationContext(),MapLocationActivity.class));

                    //startActivity(new Intent(getApplicationContext(),ProfileViewActivity.class).putExtra("user1",object.getString("success")).putExtra("user",params.get("user")));
                }
                else{
                    //sToast.makeText(getApplicationContext(),"ERROR"+object.getString("error"),Toast.LENGTH_SHORT).show();
                }
                if (object.names().get(0).equals("success1")){
                    //Toast.makeText(getApplicationContext(),"SUCCESS", Toast.LENGTH_SHORT).show();
                    Log.d("output1",object.getString("success1"));
                    //startActivity(new Intent(getApplicationContext(),MapLocationActivity.class));
                    startActivity(new Intent(getApplicationContext(),ProfileViewActivity.class).putExtra("user1",object.getString("success1")).putExtra("user",params.get("user")).putExtra("tag",params.get("tag")));
                }
                else{
                    //sToast.makeText(getApplicationContext(),"ERROR"+object.getString("error"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

}