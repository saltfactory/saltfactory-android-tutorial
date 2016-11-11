package net.saltfactory.tutorial.fcmdemo;

import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by saltfactory on 12/11/2016.
 */

/**
 * 안드로이드에서 푸시 서버로 데이터를 전송하는 HTTP 클라이언트 클래스
 */
public class HttpClient {

    private final String SERVER_URL = "http://192.168.1.100:8080";

    /**
     * 푸시서버로 사용자의 정보와 token 정보를 전송
     * @param json
     * @return
     * @throws IOException
     */
    public String saveToken(JSONObject json) throws IOException {
        return this.sendJSON("/users/save", json);
    }


    /**
     * 푸시버서로 체팅을 위해 수신자로 메세지 전달을 요청
     * @param json
     * @return
     * @throws IOException
     */
    public String sendMessage(JSONObject json) throws IOException {
        return this.sendJSON("/messages/send", json);
    }

    /**
     * HTTP 요청을 처리하는 메소드
     * @param URL
     * @param json
     * @return
     * @throws IOException
     */
    public String sendJSON(String URL , JSONObject json) throws IOException {
        HttpURLConnection conn;

        OutputStream os;
        InputStream is;
        ByteArrayOutputStream baos;

        URL url = new URL(SERVER_URL + URL);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Cache-Control", "no-cache");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);

        os = conn.getOutputStream();
        os.write(json.toString().getBytes());
        os.flush();

        String response = null;

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {

            is = conn.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] byteBuffer = new byte[1024];
            byte[] byteData = null;
            int nLength = 0;
            while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                baos.write(byteBuffer, 0, nLength);
            }
            byteData = baos.toByteArray();

            response = new String(byteData);

            Log.i(TAG, "DATA response = " + response);
        }
        
        return response;
    }
}
