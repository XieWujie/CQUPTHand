package com.example.xiewujie.cqupthand.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiewujie.cqupthand.R;
import com.example.xiewujie.cqupthand.httpUtil.HttpPostUtil;
import com.example.xiewujie.cqupthand.listener.CallbackListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LogInActivity extends AppCompatActivity {
    private EditText idEdieText;
    private EditText secretEditText;
    private ImageView backView;
    private TextView logInText;
    private static final String LOG_IN_ADDRESS = "https://wx.idsbllp.cn/api/verify";
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        idEdieText = (EditText)findViewById(R.id.user_id_edit);
        secretEditText = (EditText)findViewById(R.id.user_secret_edit);
        logInText = (TextView)findViewById(R.id.log_in_text);
        logInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
        backView = (ImageView)findViewById(R.id.back_view);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void logIn(){
        String stuNum = idEdieText.getText().toString();
        String idNum = secretEditText.getText().toString();
        String postContent = "stuNum="+stuNum+"&idNum="+idNum;
        HttpPostUtil.sendHttpRequest(LOG_IN_ADDRESS, postContent, new CallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("LogInActivity",response.toString());
                saveData(response);
                finish();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void saveData(String content) {
        editor = getSharedPreferences("userData",MODE_PRIVATE).edit();
        try {
            JSONObject object = new JSONObject(content);
            JSONObject dataObject = object.getJSONObject("data");
            String stuNum = dataObject.getString("stuNum");
            String name = dataObject.getString("name");
            String idNum = dataObject.getString("idNum");
            editor.putString("stuNum",stuNum);
            editor.putString("idNum",idNum);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
