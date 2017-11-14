package com.example.ramak.myapplication;

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

public class MessageActivity1 extends AppCompatActivity {
    private Button update;
    private String json;
    private String recv_accep;
    private String send_id;
    private String recv_id;
    private String message_content;
    private String date_sent;
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
        setContentView(R.layout.activity_message1);

        Bundle param=getIntent().getExtras();
        final String mailBox=(String) param.get("messages");
        recv_id = (String) param.get("user");
        try {

            //funcions per a cridar el string amb JSON i convertir-lo de nou a JSON
            JSONArray jsas = new JSONArray(mailBox);
            for (int i =0; i < jsas.length(); i++)
            {
                JSONObject message = jsas.getJSONObject(i);
                if (message.getString("title").equals("send_id")){
                    recv_id = message.getString("value");
                }
                /*if (message.getString("title").equals("send_id")){
                    send_id = message.getString("value");
                }
                if (message.getString("title").equals("date_sent")){
                    date_sent = message.getString("value");
                }
                if (message.getString("title").equals("recv_accep")){
                    recv_accep = message.getString("value");
                }
                if (message.getString("title").equals("message_content")) {
                    message_content = message.getString("value");
                }*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        update = (Button)findViewById(R.id.update);

        userId = (TextView) findViewById(R.id.text1);
        userId.setText("ProfileId : "+recv_id);
        userTitle = (TextView) findViewById(R.id.text2);
        userTitle.setText("Name : "+send_id);
        userEmail = (TextView) findViewById(R.id.text3);
        userEmail.setText("Email : "+mailBox);
        userSkills = (TextView) findViewById(R.id.text4);
        userSkills.setText("Skills : "+recv_accep);
        userPhone = (TextView) findViewById(R.id.text5);
        userPhone.setText("Phone : "+message_content);


        // add profile section
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateUser(recv_id);
                //database update
                //startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }
    public void updateUser(String user1) {


        HashMap<String, String> params = new HashMap<>();
        params.put("userId", user1);
        //Calling the create hero API
        MessageActivity1.PerformNetworkRequest request = new MessageActivity1.PerformNetworkRequest(Api.URL_UPDATEUSER_USER, params, CODE_POST_REQUEST);
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
