package net.saltfactory.tutorial.kakaodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.kakao.Session;
import com.kakao.SessionCallback;
import com.kakao.exception.KakaoException;
import com.kakao.template.loginbase.SampleSignupActivity;
import com.kakao.widget.LoginButton;


/**
 * Created by saltfactory on 2/11/14.
 */
public class SFKakaoStoryLoginActivity extends Activity {

    private LoginButton loginButton;
    private final SessionCallback sessionCallback = new SessionCallback() {
        @Override
        public void onSessionOpened() {
            final Intent intent = new Intent(SFKakaoStoryLoginActivity.this, SFKakaoStoryMainActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onSessionClosed(KakaoException exception) {
            loginButton.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        if(Session.initializeSession(this, sessionCallback)){
            loginButton.setVisibility(View.GONE);
        } else if (Session.getCurrentSession().isOpened()){
            sessionCallback.onSessionOpened();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        loginButton = (LoginButton) findViewById(R.id.sf_button_kakao_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setLoginSessionCallback(sessionCallback);
            }
        });
    }

}