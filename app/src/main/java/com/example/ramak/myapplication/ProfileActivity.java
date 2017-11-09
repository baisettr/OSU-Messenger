package com.example.ramak.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    private Button update;
    private String json;
    private String user1Id;
    private String user1FirstName;
    private String user1LastName;
    private String user1Email;
    private String user1Major;
    private String user1Phone;
    private String user1Skills;
    private String studyYear;
    private TextView userId;
    private TextView userTitle;
    private TextView userSkills;
    private TextView userEmail;
    private TextView userStudy;
    private TextView userPhone;
    private String title;
    private String tag;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle param=getIntent().getExtras();
        final String user=(String) param.get("user");
        try {

            //funcions per a cridar el string amb JSON i convertir-lo de nou a JSON
            JSONArray jsas = new JSONArray(user);
            for (int i =0; i < jsas.length(); i++)
            {
                JSONObject message = jsas.getJSONObject(i);
                if (message.getString("title").equals("userId")){
                    user1Id = message.getString("value");
                }
                if (message.getString("title").equals("userFirstName")){
                    user1FirstName = message.getString("value");
                }
                if (message.getString("title").equals("userLastName")){
                    user1LastName = message.getString("value");
                }
                if (message.getString("title").equals("userEmail")){
                    user1Email = message.getString("value");
                }
                if (message.getString("title").equals("userSkills")){
                    user1Skills = message.getString("value");
                }
                if (message.getString("title").equals("userMajor")){
                    user1Major = message.getString("value");
                }
                if (message.getString("title").equals("userPhone")){
                    user1Phone = message.getString("value");
                }
                if (message.getString("title").equals("studyYear")){
                    studyYear = message.getString("value");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        update = (Button)findViewById(R.id.update);

        userId = (TextView) findViewById(R.id.text1);
        userId.setText("ProfileId : "+user1Id);
        userTitle = (TextView) findViewById(R.id.text2);
        userTitle.setText("Name : "+user1FirstName+" "+user1LastName);
        userEmail = (TextView) findViewById(R.id.text3);
        userEmail.setText("Email : "+user1Email);
        userSkills = (TextView) findViewById(R.id.text4);
        userSkills.setText("Skills : "+user1Skills);
        userPhone = (TextView) findViewById(R.id.text5);
        userPhone.setText("Phone : "+user1Phone);
        userStudy = (TextView) findViewById(R.id.text6);
        userStudy.setText("Major : "+user1Major+" ("+studyYear+")");


        // add profile section
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateUser(user1Id);
                //database update
                //startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }
    public void updateUser(String user1) {


        HashMap<String, String> params = new HashMap<>();
        params.put("userId", user1);
        //Calling the create hero API
        ProfileActivity.PerformNetworkRequest request = new ProfileActivity.PerformNetworkRequest(Api.URL_UPDATEUSER_USER, params, CODE_POST_REQUEST);
        request.execute();
    }


    //inner class to perform network request extending an AsyncTask
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

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

                if (object.names().get(0).equals("success")){
                    //Toast.makeText(getApplicationContext(),"SUCCESS", Toast.LENGTH_SHORT).show();
                    Log.d("output",object.getString("success"));
                    //startActivity(new Intent(getApplicationContext(),MapLocationActivity.class));

                    //startActivity(new Intent(getApplicationContext(),ProfileViewActivity.class).putExtra("user1",object.getString("success")).putExtra("user",params.get("user")));
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
