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
        try {
            sendRegistrationToServer(refreshedToken);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRegistrationToServer(String token) throws JSONException, IOException {
        // 푸시서버로 token을 전송하는 코드 추가
        HttpURLConnection conn    = null;

        OutputStream os   = null;
        InputStream is   = null;
        ByteArrayOutputStream baos = null;

        URL url = new URL("http://서버주소:8080/tokens/save");
        conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Cache-Control", "no-cache");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);

        JSONObject json = new JSONObject();
        json.put("token", token);

        os = conn.getOutputStream();
        os.write(json.toString().getBytes());
        os.flush();

        String response;

        int responseCode = conn.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK) {

            is = conn.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] byteBuffer = new byte[1024];
            byte[] byteData = null;
            int nLength = 0;
            while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                baos.write(byteBuffer, 0, nLength);
            }
            byteData = baos.toByteArray();

            response = new String(byteData);

            Log.i(TAG, "DATA response = " + response);
        }
    }
}
