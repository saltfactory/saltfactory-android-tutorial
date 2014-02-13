package net.saltfactory.tutorial.kakaodemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.kakao.*;
import com.kakao.helper.Logger;

import java.io.*;

/**
 * Created by saltfactory@gmail.com
 * on 2/12/14.
 */
public class SFKakaoStoryPostActivity extends Activity {
    private final String TAG = "saltfactory.net";

    private static int RESULT_LOAD_IMAGE = 1;

    // 사진 앨범에서 선택한 사진을 미리 보여주는 이미지뷰
    private ImageView imageView;
    // 사진 앨범에서 선택한 사진을 저장할 비트맵
    private Bitmap bitmapSelectedPhoto;
    // 카카오스토리에 글을 포스팅하기 위한 글상자
    private EditText editText;
    // 카카오스토리로 포스팅하기 위한 버튼
    private Button button;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.post);

        imageView = (ImageView) findViewById(R.id.sf_imageview_thumb);
        button = (Button) findViewById(R.id.sf_button_post);
        editText = (EditText) findViewById(R.id.sf_edittext_content);

        // 엑티비티가 실행하면 사진을 선택할 수 있도록 사집첩 앨범을 열도록 한다.
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);

        // 카카오스토리로 포스팅하는 버튼의 클릭리스너 등록
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    // 사진첩 앨범에서 선택한 사진을 카카오스토리에 올리기 위한 사이즈를 조절하고 그 파일을 업로드에 사용한다.
                    File file = new File(writeStoryImage(getApplicationContext(), bitmapSelectedPhoto));
                    KakaoStoryService.requestUpload(new MyKakaoStoryHttpResponseHandler<KakaoStoryUpload>() {
                        /**
                         * 카카오스토리에 사진 파일 업로드가 완료하면 이 메소드가 자동적으로 호출되어지는데
                         * 이때 사진 파일 업로드한 이미지 경로를 가지고 KakaoStoryPostParamBuilder로 파라미터를 설정해서 글 내용과 함께 포스팅을 요청한다.
                         * @param resultObj 성공한 결과
                         */
                        @Override
                        protected void onHttpSuccess(KakaoStoryUpload resultObj) {
                            String imageURL = resultObj.getUrl();

                            Log.d(TAG, imageURL);
                            sendPostWithImageURL(imageURL);
                        }
                    }, file);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getLocalizedMessage());
                }


            }
        });
    }


    private void sendPostWithImageURL(String imageURL){
        String storyPostText = editText.getText().toString();
        final KakaoStoryPostParamBuilder postParamBuilder = new KakaoStoryPostParamBuilder(storyPostText, KakaoStoryPostParamBuilder.PERMISSION.PUBLIC);

        if (imageURL != null)
            postParamBuilder.setImageURL(imageURL);
        Bundle parameters = postParamBuilder.build();

        KakaoStoryService.requestPost(new MyKakaoStoryHttpResponseHandler<Void>() {
            @Override
            protected void onHttpSuccess(Void resultObj) {
                Toast.makeText(getApplicationContext(), "success to post on KakaoStory", Toast.LENGTH_SHORT).show();
//                                    SFKakaoStoryPostActivity.super.onBackPressed();
            }
        }, parameters);
    }




    /**
     * 사진 앱범을 열어서 사진을 선택하고 닫으면 이 메소드가 호출 된다.
     * 파일을 카카오스토리에 올리기 전에 썸네일 이미지로 imageView에 보여주게 한다.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
//
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;

            bitmapSelectedPhoto = BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(bitmapSelectedPhoto);

        } else {
            super.onBackPressed();
        }
    }


    /**
     * 카카오스토리에 사진파일을 전송하기 위해서 사이즈를 변경하는 메소드
     *
     * @param context
     * @param bitmap
     * @return
     * @throws IOException
     */
    private static String writeStoryImage(final Context context, final Bitmap bitmap) throws IOException {
        final File diskCacheDir = new File(context.getCacheDir(), "story");

        if (!diskCacheDir.exists())
            diskCacheDir.mkdirs();

        final String file = diskCacheDir.getAbsolutePath() + File.separator + "temp_" + System.currentTimeMillis() + ".jpg";

        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file), 8 * 1024);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } finally {
            if (out != null) {
                out.close();
            }
        }

        return file;
    }


    /**
     * 카카오스토리에 Http 요청을한 이후 응답받는 결과에 따라 동작하게 하는 구현체
     *
     * @param <T>
     */
    private abstract class MyKakaoStoryHttpResponseHandler<T> extends KakaoStoryHttpResponseHandler<T> {

        @Override
        protected void onHttpSessionClosedFailure(final APIErrorResult errorResult) {
            Log.e(TAG, errorResult.toString());
        }

        @Override
        protected void onNotKakaoStoryUser() {
            Toast.makeText(getApplicationContext(), "not KakaoStory user", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onFailure(final APIErrorResult errorResult) {
            final String message = "MyKakaoStoryHttpResponseHandler : failure : " + errorResult;
            Logger.getInstance().d(message);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}