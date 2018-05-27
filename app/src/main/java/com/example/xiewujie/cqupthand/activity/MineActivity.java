package com.example.xiewujie.cqupthand.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.xiewujie.cqupthand.R;
import com.example.xiewujie.cqupthand.httpUtil.HttpGetUtil;
import com.example.xiewujie.cqupthand.httpUtil.HttpPostUtil;
import com.example.xiewujie.cqupthand.listener.CallbackListener;
import com.example.xiewujie.cqupthand.listener.GetInputListener;
import com.example.xiewujie.cqupthand.tool.CircleImageView;

import org.json.JSONObject;

import java.io.InputStream;

public class MineActivity extends AppCompatActivity {

    private CircleImageView circleImageView;
    private TextView nickNameText;
    private TextView introductionText;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        circleImageView = (CircleImageView)findViewById(R.id.user_src);
        nickNameText = (TextView)findViewById(R.id.user_nick_name);
        introductionText = (TextView)findViewById(R.id.user_signature);
        initUserData();
    }

    private void initUserData(){
        SharedPreferences preferences = getSharedPreferences("userData",MODE_PRIVATE);
        String stuNum = preferences.getString("stuNum",null);
        String idNum = preferences.getString("idNum",null);
        String postContent = "stuNum="+stuNum+"&idNum="+idNum;
        Log.e("MineActivity",postContent);
        String address = "https://wx.idsbllp.cn/cyxbsMobile/index.php/Home/Person/search";
        HttpPostUtil.sendHttpRequest(address, postContent, new CallbackListener() {
            @Override
            public void onFinish(String response) {
                resolveData(response);
                Log.e("MineActivity",response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    private void resolveData(String content){
        try {
            JSONObject object = new JSONObject(content);
            JSONObject ob = object.getJSONObject("data");
            final String nickname = ob.getString("nickname");
            final String introduction = ob.getString("introduction");
            String photo_src = ob.getString("photo_src");
            Log.e("MineActivity",photo_src);
            HttpGetUtil.getInputStram(photo_src, new GetInputListener() {
                @Override
                public void onInputStream(InputStream inputStream) {
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            nickNameText.setText(nickname);
                            introductionText.setText(introduction);
                         //   circleImageView.setImageBitmap(bitmap);
                        }
                    });
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
