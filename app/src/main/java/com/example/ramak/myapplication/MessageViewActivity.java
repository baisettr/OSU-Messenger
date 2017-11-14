package com.example.ramak.myapplication;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageViewActivity extends AppCompatActivity {
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

        List<String> messageList = new ArrayList<String>();


        Bundle param=getIntent().getExtras();
        final String user1=(String) param.get("recv_id");
        recv_id = user1;
        final String user2=(String) param.get("send_id");
        send_id = user2;
        final String mailBox=(String) param.get("messages");
        try {

            //funcions per a cridar el string amb JSON i convertir-lo de nou a JSON
            JSONArray jsas = new JSONArray(mailBox);

            for (int i = 0; i < jsas.length(); i++) {
                JSONObject message = jsas.getJSONObject(i);

                if (message.getString("title").equals("recv_id")){
                    //recv_id = message.getString("value");
                    //senderList.add(send_id);
                }
                if (message.getString("title").equals("send_id")){
                    //send_id = message.getString("value");
                    //senderList.add(send_id);
                }
                if (message.getString("title").equals("date_sent")){
                    ///date_sent = message.getString("value");
                    //senderList.add(send_id);
                }
                if (message.getString("title").equals("message_content")){
                    message_content = message.getString("value");
                    messageList.add(message_content);
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
        for(int i = 0; i < messageList.size(); i++) {
            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.defaultprofileimage),
                    messageList.get(i), "message contents");
        }


        // when list item is clicked, show the history of messages between the user and the user whose name is clinked on the list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item



                MessageBoxItem item = (MessageBoxItem) parent.getItemAtPosition(position);
                //getMessages(recv_id,item.getTitle());
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
                    Toast.makeText(getApplicationContext(),"SUCCESS", Toast.LENGTH_SHORT).show();
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
