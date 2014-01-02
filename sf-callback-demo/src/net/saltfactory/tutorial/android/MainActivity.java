package net.saltfactory.tutorial.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import net.saltfactory.tutorial.android.callback.SFCallback;
import net.saltfactory.tutorial.android.callback.SFCallbackWithParams;
import net.saltfactory.tutorial.android.task.SFAsyncTaskComparedWithNumbers;

import java.util.HashMap;

public class MainActivity extends Activity {
    final private String TAG = "saltfactory.net";
    /**
     * Called when the activity@gmail.com is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new SFAsyncTaskComparedWithNumbers(new SFCallbackWithParams() {
            // preprecoessCallback 구현
            @Override
            public void callback(HashMap<String, Object> params) {
                Log.d(TAG, "call " + params.get("data") +" from MainActivity");
            }
        },
        new SFCallbackWithParams() {
            // successCallback 구현
            @Override
            public void callback(HashMap<String, Object> params) {
                Log.d(TAG, "call " + params.get("data") +" from MainActivity");
            }
        },
        new SFCallbackWithParams() {
            // failCallback 구현
            @Override
            public void callback(HashMap<String, Object> params) {
                Log.d(TAG, "call " + params.get("data") + " from MainActivity");
            }
        }).execute(1, 1);

    }
}
