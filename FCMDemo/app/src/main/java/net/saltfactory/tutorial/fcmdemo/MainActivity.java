package net.saltfactory.tutorial.fcmdemo;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends ListActivity {

    ArrayList<Message> messages;
    MessageRowAdapter adapter;
    EditText text;
    static Random rand = new Random();
    static String sender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText) this.findViewById(R.id.text);

        messages = new ArrayList<Message>();

        adapter = new MessageRowAdapter(this, messages);
        setListAdapter(adapter);
    }

    public void sendMessage(View v) {
        String newMessage = text.getText().toString().trim();
        if (newMessage.length() > 0) {
            text.setText("");
            Message message = new Message(newMessage, true);
            addNewMessage(message);
//            new SendMessage().execute(message);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter("messageReciver"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(mMessageReceiver);
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            addNewMessage(new Message(message, false));

        }
    };


    private void addNewMessage(Message m) {
        messages.add(m);
        adapter.notifyDataSetChanged();
        getListView().setSelection(messages.size() - 1);
    }

    private class SendMessage extends AsyncTask<Message, String, String> {
        @Override
        protected String doInBackground(Message... params) {
            Message message = params[0];
            HttpClient httpClient = new HttpClient();
            JSONObject json = new JSONObject();
            try {
                json.put("reciever", "userA");
                json.put("message", message.getMessage());
                httpClient.sendMessage(json);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return message.getMessage();

        }

        @Override
        protected void onPostExecute(String text) {
            if(messages.size() > 0){
                if (messages.get(messages.size() - 1).isStatusMessage)
                {
                    messages.remove(messages.size() - 1);
                }
            }

            addNewMessage(new Message(text, true));
        }


    }


}
