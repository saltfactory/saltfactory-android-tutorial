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

public class HttpClient {

    private final String SERVER_URL = "http://192.168.1.100:8080";

    public String saveToken(JSONObject json) throws IOException {
        return this.sendJSON("/users/save", json);
    }


    public String sendMessage(JSONObject json) throws IOException {
        return this.sendJSON("/messages/send", json);
    }

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
