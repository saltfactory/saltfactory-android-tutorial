package net.saltfactory.tutorial.android.task;

import android.os.AsyncTask;
import net.saltfactory.tutorial.android.callback.SFCallback;
import net.saltfactory.tutorial.android.callback.SFCallbackWithParams;

import java.util.HashMap;
import java.util.Observable;

/**
 * Created by saltfactory@gmail.com on 1/2/14.
 */
public class SFAsyncTaskComparedWithNumbers extends AsyncTask<Number, Void, Boolean> {

    private SFCallbackWithParams preprocessCallback;
    private SFCallbackWithParams successCallback;
    private SFCallbackWithParams failCallback;

    public SFAsyncTaskComparedWithNumbers(SFCallbackWithParams preprocessCallback, SFCallbackWithParams successCallback, SFCallbackWithParams failCallback){
        this.preprocessCallback = preprocessCallback;
        this.successCallback = successCallback;
        this.failCallback = failCallback;
    }

    @Override
    protected Boolean doInBackground(Number... numbers) {

        if (preprocessCallback != null){
            HashMap<String, Object>params = new HashMap<String, Object>();
            params.put("data", "preprocessCallback");
            preprocessCallback.callback(params);
        }

        return numbers[0].equals(numbers[1]);
    }


    @Override
    protected void onPostExecute(Boolean success){

        HashMap<String, Object>params = new HashMap<String, Object>();

        if (success){
                params.put("data", "successCallback");
                successCallback.callback(params);
        } else {
            params.put("data", "failCallback");
            failCallback.callback(params);
        }
    }
}
