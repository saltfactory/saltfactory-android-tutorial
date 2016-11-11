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

    /**
     * 안드로이드 디바이스 토큰 정보를 획득하여 푸시서버로 전송하는 메소드
     * 서버로 토큰 정보를 전송할 때, 사용자 정보를 함께 전송해서 이 token이 어떤 사용자의 토큰인지 데이터베이스에 저장한다.
     * 예제에서는 고정값으로 userA 라고 username을 함께 보내도록 하였다
     * @param token
     */
    private void sendRegistrationToServer(String token) {
        JSONObject json = new JSONObject();
        HttpClient httpClient = new HttpClient();

        try {
            json.put("username", "userA"); //TODO: 사용자 정보 값, 이후 구현할 때는 UI 입력 폼으로 부터 사용자 정보를 받아 오도록 한다
            json.put("token", token);

            httpClient.saveToken(json);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
