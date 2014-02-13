package net.saltfactory.tutorial.kakaodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.kakao.Session;
import com.kakao.SessionCallback;
import com.kakao.exception.KakaoException;
import com.kakao.widget.LoginButton;

/**
 * Created by saltfactory@gmail.com 
 * on 2/12/14.
 */
public class SFKakaoLoginActivity extends Activity {

    // 카카오 SDK에 포함된 카카오로그인 버튼 객체
    private LoginButton loginButton;

    // 카카오 세션콜백
    private final SessionCallback sessionCallback = new SessionCallback() {

        /**
         * 카카오 세션이 있을 경우 MyActivity를 새로운 intent로 시작한다.
         */
        @Override
        public void onSessionOpened() {
            final Intent intent = new Intent(SFKakaoLoginActivity.this, MyActivity.class);
            startActivity(intent);
            finish();
        }

        /**
         * 카카오 세션이 없을 경우
         * @param exception   close된 이유가 에러가 발생한 경우에 해당 exception.
         */
        @Override
        public void onSessionClosed(KakaoException exception) {
            loginButton.setVisibility(View.VISIBLE);
        }
    };

    /**
     * 카카오 SDK를 사용할 경우 카카오 SDK의 com.kakao.LoginActivity를 새로운 intent로 사용해서 로그인을 하는데,
     * 로그인 처리가 마치고 다시 이 클래스로 돌아오면 onResume()을 호출한다. 화면에서 사라졌다가 다시 나타날때 카카오 세션을 검사하도록 한다.
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (Session.initializeSession(this, sessionCallback)) {
            loginButton.setVisibility(View.GONE);
        } else if (Session.getCurrentSession().isOpened()) {
            sessionCallback.onSessionOpened();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // login.xml 레이아웃에 안드로이드 로그인 버턴을 정의했다.
        setContentView(R.layout.login);

        loginButton = (LoginButton) findViewById(R.id.sf_button_kakao_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 카카오 로그인 버튼에 세션 콜백을 넘겨준다. 세션 콜백은 위에 정의해두었다.
                loginButton.setLoginSessionCallback(sessionCallback);
            }
        });
    }

}