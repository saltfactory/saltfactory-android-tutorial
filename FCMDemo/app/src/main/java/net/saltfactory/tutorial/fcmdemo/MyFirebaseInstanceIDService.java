package net.saltfactory.tutorial.fcmdemo;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by saltfactory on 10/11/2016.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        // 안드로이드 앱이 구동하면 실제 토큰을 획득하게 되는데 획득한 토큰을 푸시 서버로 전송하는 메소드를 추가 구현해야한다.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        JSONObject json = new JSONObject();
        HttpClient httpClient = new HttpClient();

        try {
            json.put("username", "userA");
            json.put("token", token);

            httpClient.saveToken(json);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
