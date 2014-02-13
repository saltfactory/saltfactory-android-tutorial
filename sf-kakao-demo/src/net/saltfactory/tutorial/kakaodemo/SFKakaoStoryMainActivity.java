package net.saltfactory.tutorial.kakaodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.kakao.*;

import java.util.Calendar;
import java.util.Locale;
//import com.kakao.sample.kakaostory.KakaoStoryLoginActivity;

/**
 * Created by saltfactory on 2/11/14.
 */
public class SFKakaoStoryMainActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        Button buttonProfile = (Button) findViewById(R.id.sf_button_kakaostory_profile);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(SFKakaoStoryMainActivity.this, SFKakaoStoryProfileActivity.class);
                startActivity(intent);
            }
        });

        Button buttonPost = (Button) findViewById(R.id.sf_button_kakaostory_post);
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(SFKakaoStoryMainActivity.this, SFKakaoStoryPostActivity.class);
                startActivity(intent);
            }
        });

        Button buttonLogout = (Button) findViewById(R.id.sf_button_kakao_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagement.requestLogout(new LogoutResponseCallback() {
                    @Override
                    protected void onSuccess(long userId) {
                        final Intent intent = new Intent(SFKakaoStoryMainActivity.this, SFKakaoStoryLoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    protected void onFailure(APIErrorResult errorResult) {

                    }
                });
            }
        });


    }
}