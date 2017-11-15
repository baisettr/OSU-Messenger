package com.example.ramak.myapplication;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MessageActivity extends AppCompatActivity {
    private Button update;
    private String json;
    private String status;
    private String send_id;
    private String recv_id;
    private String message_content;
    private String date_sent;
    private String title;
    private String tag;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        MessageBoxAdapter adapter;
        adapter = new MessageBoxAdapter();

        listView = (ListView) findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        List<String> senderList = new ArrayList<String>();


        Bundle param=getIntent().getExtras();
        final String user=(String) param.get("user");
        recv_id = user;
        final String mailBox=(String) param.get("usersList");
        try {

            //funcions per a cridar el string amb JSON i convertir-lo de nou a JSON
            JSONArray jsas = new JSONArray(mailBox);

            for (int i = 0; i < jsas.length(); i++) {
                JSONObject message = jsas.getJSONObject(i);

                if (message.getString("title").equals("friendId")){
                    send_id = message.getString("value");
                    senderList.add(send_id);
                }
                if (message.getString("title").equals("userStatus")){
                    status = message.getString("value");
                    //senderList.add(send_id);
                }

                /*
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

        //show all the users' name who messaging with the current user
        for(int i = 0; i < senderList.size(); i++) {
            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.defaultprofileimage),
                    senderList.get(i), "message contents");
        }


        // when list item is clicked, show the history of messages between the user and the user whose name is clinked on the list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item



                MessageBoxItem item = (MessageBoxItem) parent.getItemAtPosition(position);
                // retrieve messages
                getMessages(recv_id,item.getTitle());
                String sendUser = item.getTitle();
                String contents = item.getDesc();
                Drawable iconDrawable = item.getIcon();



                Toast.makeText(getApplicationContext(), sendUser + " is clicked   Message:" + contents, Toast.LENGTH_SHORT).show();


                // TODO : use item data.
            }
        });
    }


    public void getMessages(String user1,String user2) {

        HashMap<String, String> params = new HashMap<>();
        params.put("recv_id", user1);
        params.put("send_id", user2);
        //Calling the create hero API
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_GETMESSAGES_USER, params, CODE_POST_REQUEST);
        request.execute();
    }

    public void insertMessage(String user1,String user2, String message) {

        HashMap<String, String> params = new HashMap<>();
        params.put("recv_id", user1);
        params.put("send_id", user2);
        params.put("message", message);
        //Calling the create hero API
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_INSERTMESSAGE_USER, params, CODE_POST_REQUEST);
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
            try {
                JSONObject object = new JSONObject(s);
                Log.d("here1",object.toString());

                //   output when messagesList is retrieved - object.getString("success")
                if (object.names().get(0).equals("success1")){
                    Toast.makeText(getApplicationContext(),"SUCCESS", Toast.LENGTH_SHORT).show();
                    Log.d("output",object.getString("success1"));

                    startActivity(new Intent(getApplicationContext(),MessageViewActivity.class).putExtra("messages",object.getString("success1")).putExtra("user",params.get("recv_id")).putExtra("send_id",params.get("send_id")));
                }

                //   output when a new message is inserted - object.getString("success1")
                else if (object.names().get(0).equals("success")){
                    Toast.makeText(getApplicationContext(),"SUCCESS", Toast.LENGTH_SHORT).show();
                    Log.d("output",object.getString("success"));
}
                else{
                    Toast.makeText(getApplicationContext(),"ERROR"+object.getString("error"),Toast.LENGTH_SHORT).show();
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
