package net.saltfactory.tutorial.httpsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {
    final String TAG = "saltfactory.net";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button buttonGet = (Button) findViewById(R.id.sf_button_post);
        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        //HttpClient HttpClient = new DefaultHttpClient();
//                        HttpClient httpClient = getHttpClient();


                        String urlString = "https://192.168.1.101/login";
//                        try {
//                            URI url = new URI(urlString);
//
//                            HttpPost httpPost = new HttpPost();
//                            httpPost.setURI(url);
//
//                            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
//                            nameValuePairs.add(new BasicNameValuePair("userId", "saltfactory"));
//                            nameValuePairs.add(new BasicNameValuePair("password", "password"));
//
//                            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//
//                            HttpResponse response = httpClient.execute(httpPost);
//                            String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
//
//                            Log.d(TAG, responseString);
//
//                        } catch (URISyntaxException e) {
//                            Log.e(TAG, e.getLocalizedMessage());
//                            e.printStackTrace();
//                        } catch (ClientProtocolException e) {
//                            Log.e(TAG, e.getLocalizedMessage());
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            Log.e(TAG, e.getLocalizedMessage());
//                            e.printStackTrace();
//                        }
//
                        try {
                            URL url = new URL(urlString);

//                            HttpURLConnection connection = null;
//                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                            trustAllHosts();
                            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                            httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String s, SSLSession sslSession) {
                                    return true;
                                }
                            });

                            HttpURLConnection connection = httpsURLConnection;

                            connection.setRequestMethod("POST");
                            connection.setDoInput(true);
                            connection.setDoOutput(true);

                            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
                            nameValuePairs.add(new BasicNameValuePair("userId", "saltfactory"));
                            nameValuePairs.add(new BasicNameValuePair("password", "password"));

                            OutputStream outputStream = connection.getOutputStream();
                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                            bufferedWriter.write(getURLQuery(nameValuePairs));
                            bufferedWriter.flush();
                            bufferedWriter.close();
                            outputStream.close();

                            connection.connect();


                            StringBuilder responseStringBuilder = new StringBuilder();
                            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                for (;;){
                                    String stringLine = bufferedReader.readLine();
                                    if (stringLine == null ) break;
                                    responseStringBuilder.append(stringLine + '\n');
                                }
                                bufferedReader.close();
                            }

                            connection.disconnect();

                            Log.d(TAG, responseStringBuilder.toString());



                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                };

                thread.start();
            }
        });

    }

    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType)
                    throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub

            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType)
                    throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub

            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getURLQuery(List<BasicNameValuePair> params){
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;

        for (BasicNameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                stringBuilder.append("&");

            try {
                stringBuilder.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }

    private HttpClient getHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SFSSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }
}
