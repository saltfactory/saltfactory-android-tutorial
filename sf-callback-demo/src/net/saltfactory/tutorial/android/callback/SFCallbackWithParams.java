package net.saltfactory.tutorial.android.callback;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by saltfactory on 1/2/14.
 */
public interface SFCallbackWithParams extends Serializable {
    public void callback(HashMap<String, Object> params);
}
