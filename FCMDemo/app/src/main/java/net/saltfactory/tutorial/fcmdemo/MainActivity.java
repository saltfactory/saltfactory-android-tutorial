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


    /**
     * 안드로이드 앱 뷰를 초기화 한다. 이 때 채팅 메세지들을 메모리에 가지고 있기 이해서 messages 라는 리스트를 선언하고 메세지를 이 리스트에 추가한다.
     * 메제지를 화면에 처리하기 위한 MessageRowAdatper도 정의하여 채팅화면의 스크롤을 담당하는 List의 dapater로 지정한다.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText) this.findViewById(R.id.text);

        messages = new ArrayList<Message>();

        adapter = new MessageRowAdapter(this, messages);
        setListAdapter(adapter);
    }

    /**
     * 사용자가 화면에서 전송 버튼을 누르면 이 메소드가 동작하는데, 이 때 작성한 메세제를 푸시서버로 전송하는 SendMessage를 호출한다.
     * 정상적으로 서버에 저장이되면 내 화면을 갱신한다.
     * @param v
     */
    public void sendMessage(View v) {
        String newMessage = text.getText().toString().trim();
        if (newMessage.length() > 0) {
            text.setText("");
            Message message = new Message(newMessage, true);
            addNewMessage(message);
//            new SendMessage().execute(message);
        }
    }

    /**
     * MyFirebaseMssagingService에서 CFM 메세지를 수신하게 되면 이 곳 Activity를 갱신하기 위해서 messageReciver로 broadcast를 하는데
     * 앱의 화면이 열리게될 때 컨텍스트에 등록 해야지 broadcast를 사용할 수 있다
     */
    @Override
    public void onResume() {
        super.onResume();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter("messageReciever"));
    }

    /**
     * 화면 닫히게 되면 등록한 broadcast를 해지한다.
     */
    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(mMessageReceiver);
    }


    /**
     * Boroadcast 로 메세지를 전송받게 되면 실제 채팅화면에 메세지를 갱신하기 위해선 addNewMessage() 함수를 호출한다.
     * 이때 내가 전송한 메세지가 아니기 때문에 isMine 파라미터를 false로 지정한다
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");

            addNewMessage(new Message(message, false));

        }
    };

    /**
     * 새로운 메세지가 등록되면 화면을 갱신하는 메소드이다. 뷰에 연동된 모델을 업데이트하면 자동으로 뷰가 새롭게 렌더링이 된다.
     * @param m
     */
    private void addNewMessage(Message m) {
        messages.add(m);
        adapter.notifyDataSetChanged();
        getListView().setSelection(messages.size() - 1);
    }

    /**
     * 사용자가 채팅창에서 전송 버튼을 누르면 메세제 내용을 수신자 정보와 함께 푸시 서버로 채팅 메세지를 CFM 으로 보내달라고 요청하는 메소드이다.
     * 에제를 위해서  SendMessage를 연결하지 않았지만, 위에서 사용자가 전송버튼을 누르는 이벤트처리 함수 안에서 이 AsyncTask 클래스를 실행시킨다.
     */
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

        /**
         * 서버로 메세지 전송 요청이 끝나면 실제 모바일 화면을 갱신한다.
         * @param text
         */
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
