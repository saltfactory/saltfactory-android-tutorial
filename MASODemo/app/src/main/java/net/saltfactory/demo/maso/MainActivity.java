package net.saltfactory.demo.maso;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.saltfactory.demo.maso.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setToolbar();


        setDataBinding();

    }

    public void setDataBinding(){
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        final User user = new User("길동", "홍");
        binding.setUser(user);
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {

                                          String text = ( stringFromJNI() );
                                          user.setFirstName(text);
//                                          user.setFirstName("GilDong");
                                      }
                                  },
                1000);    }

//    public void setToolbar(){
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        final ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.android);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public native String  stringFromJNI();

    public native String  unimplementedStringFromJNI();

    static {
        System.loadLibrary("hello-jni");
    }


}
