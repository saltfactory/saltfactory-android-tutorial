package net.saltfactory.demo.maso;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by saltfactory on 6/17/15.
 */
public class User extends BaseObservable {
    private String firstName;
    private String lastName;
    private boolean isAdult;

    @Bindable
    public String getFirstName() {
        return this.firstName;
    }

    @Bindable
    public String getLastName() {
        return this.lastName;
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(net.saltfactory.demo.maso.BR.firstName);
    }

    @Bindable
    public boolean getIsAdult() {
        return isAdult;
    }

    public void setIsAdult(boolean isAdult) {
        this.isAdult = isAdult;
        notifyPropertyChanged(net.saltfactory.demo.maso.BR.isAdult);
    }
}
