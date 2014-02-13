package net.saltfactory.tutorial.kakaodemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.kakao.AppActionBuilder;
import com.kakao.KakaoLink;
import com.kakao.KakaoLinkParseException;
import com.kakao.KakaoTalkLinkMessageBuilder;

/**
 * Created by saltfactory on 2/12/14.
 */
public class SFKakaoLinkActivity extends Activity {
    private KakaoLink kakaoLink;
    private KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link);

        final String stringText = "카카오링크 테스트 텍스트";
        final String stringImage = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn2/t1/p320x320/1377444_10200267928260600_37355712_n.jpg";
        final String stringUrl = "http://blog.saltfactory.net";

        try {
            kakaoLink = KakaoLink.getKakaoLink(this);
            kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
        } catch (KakaoLinkParseException e) {
            e.printStackTrace();
            alert(e.getMessage());
        }


        Button button = (Button) findViewById(R.id.sf_button_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    kakaoTalkLinkMessageBuilder.addText(stringText);
                    kakaoTalkLinkMessageBuilder.addImage(stringImage, 320, 320);
                    kakaoTalkLinkMessageBuilder.addWebLink("블로그 이동", stringUrl);

                    kakaoTalkLinkMessageBuilder.addAppButton("앱열기",
                            new AppActionBuilder()
                                    .setAndroidExecuteURLParam("target=main")
                                    .setIOSExecuteURLParam("target=main", AppActionBuilder.DEVICE_TYPE.PHONE).build());

                    kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder.build());

                } catch (KakaoLinkParseException e) {
                    e.printStackTrace();
                    alert(e.getMessage());
                }


            }
        });
    }

    private void alert(String message) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .create().show();
    }
}