package net.saltfactory.tutorial.fcmdemo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import static com.google.android.gms.internal.zzs.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // 실제 메세지를 받았을 때 처리하는 부분
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        updateMyActivity(getApplicationContext(), remoteMessage.getNotification().getBody());
    }

    /**
     * 실제 CFM으로 메세지를 수신하게 되면 나의 Activity를 업데이트한다.
     * 내가 지정한 Activity를 바로 갱신하는 통신을 하기 위해서 Broadcast를 사용한다. 이 때 이름은 messageReciever로 지정하였다
     * 입력 받은 message는 message 이름으로 넘겨주게 된다.
     * @param context
     * @param message
     */
    public void updateMyActivity(Context context, String message) {

        Intent intent = new Intent("messageReciever");

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        context.sendBroadcast(intent);
    }
}
