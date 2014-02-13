package net.saltfactory.tutorial.kakaodemo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.kakao.*;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by saltfactory on 2/12/14.
 */
public class SFKakaoStoryProfileActivity extends Activity {

    private final String TAG = "saltfactory.net";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile);

        // 카카오스토리 프로파일 사진을 보여줄 이미지뷰
        final ImageView imageViewProfile = (ImageView) findViewById(R.id.sf_imageview_profile);
        // 카카오스토리 배경화면 사진을 보여줄 이미지뷰
        final ImageView imageViewBackground = (ImageView) findViewById(R.id.sf_imageview_background);
        // 카카오스토리 닉네임을 보여줄 텍스트뷰
        final TextView textViewNickName = (TextView) findViewById(R.id.sf_textview_nickname);
        // 카카오스토리 생일을 보여줄 텍스트뷰
        final TextView textViewBirthday = (TextView) findViewById(R.id.sf_textview_birthday);


        // 카카오스토리 API 중에 프로파일 요청을 하기 위해서 KakaoStoryService.requestProfile()을 호출한다.
        // 이 때 결과응답을 처리하 수 있도록 KakaoStoryHttpResponsehandler 구현체를 보내어 요청한다.
        KakaoStoryService.requestProfile(new KakaoStoryHttpResponseHandler<KakaoStoryProfile>() {
            /**
             * 카카오 세션의 유저와 같지 않을 때, 즉 카카오스토리 계정으로 로그인 되지 않은 경우
             */
            @Override
            protected void onNotKakaoStoryUser() {
                final Intent intent = new Intent(SFKakaoStoryProfileActivity.this, SFKakaoStoryLoginActivity.class);
                startActivity(intent);
                finish();
            }

            /**
             * 카카오스토리 requestProfile 요청이 실패하였을 경우
             * @param errorResult 실패한 원인이 담긴 결과
             */
            @Override
            protected void onFailure(APIErrorResult errorResult) {
                Log.e(TAG, errorResult.toString());
            }

            /**
             * 카카오스토리 requestProfile 요청을 성공하였을 경우 KakaoStoryProfile 객체를 받아오게 된다.
             * KakaoStoryProfile 객체 안에 프로파일 이미지, 배경이미지, 닉네임, 생일 정보가 들어 있다.
             * @param kakaoStoryProfile
             */
            @Override
            protected void onHttpSuccess(KakaoStoryProfile kakaoStoryProfile) {
                String nickName = kakaoStoryProfile.getNickName();
                Log.d(TAG, "KakaoStory nickName : " + nickName);
                textViewNickName.setText("이름 : " + nickName);


                String profileImageURL = kakaoStoryProfile.getProfileImageURL();
                Log.d(TAG, "KakaoStory profileImageURL : " + profileImageURL);
                if (profileImageURL != null) {
                    new DownloadImageTask(imageViewProfile).execute(profileImageURL);
                }


                String backgroundURL = kakaoStoryProfile.getBgImageURL();
                Log.d(TAG, "KakaoStory backgroundURL : " + backgroundURL);
                if (backgroundURL != null) {
                    new DownloadImageTask(imageViewBackground).execute(backgroundURL);
                }


                Calendar birthday = kakaoStoryProfile.getBirthdayCalendar();
                if (birthday != null) {
                    StringBuilder displayBirthday = new StringBuilder(8);
                    displayBirthday.append(birthday.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)).append(" ").append(birthday.get(Calendar.DAY_OF_MONTH));

                    KakaoStoryProfile.BirthdayType birthDayType = kakaoStoryProfile.getBirthdayType();

                    if (birthDayType != null)
                        displayBirthday.append(" (").append(birthDayType.getDisplaySymbol()).append(")");
                    textViewBirthday.setText("생일 : " + displayBirthday);
                    //Log.d(TAG, "KakaoStory birthday : " + displayBirthday);
                }

            }

            @Override
            protected void onHttpSessionClosedFailure(APIErrorResult errorResult) {
                Log.e(TAG, errorResult.toString());
            }
        });

    }


    /**
     * 비동기 방식으로 이미지 URL을 가지고 안드로이드 레이아웃에 등록한 ImageView에 사진을 로드 시키는 구현체
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}