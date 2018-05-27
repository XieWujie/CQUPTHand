package com.example.xiewujie.cqupthand.activity;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.xiewujie.cqupthand.R;
import com.example.xiewujie.cqupthand.adapter.QARcAdapter;
import com.example.xiewujie.cqupthand.httpUtil.HttpPostUtil;
import com.example.xiewujie.cqupthand.json.QaHeadQuestion;
import com.example.xiewujie.cqupthand.json.QaQuestionResolve;
import com.example.xiewujie.cqupthand.listener.CallbackListener;

import org.json.JSONObject;

import java.util.List;

public class QAHomeActivity extends AppCompatActivity {
    private List<QaHeadQuestion> mList;
    private RecyclerView recyclerView;
    private QARcAdapter adapter;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qahome);
        recyclerView = (RecyclerView)findViewById(R.id.q_a_head_rc_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        postData();
    }

    private void postData(){
        String address = "https://wx.idsbllp.cn/springtest/cyxbsMobile/index.php/QA/Question/getQuestionList";
        String postContent = "kind=全部";
        HttpPostUtil.sendHttpRequest(address, postContent, new CallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("QaHomeActivity",response);
                resolveData(response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    private void resolveData(final String content){
                    try {
                        JSONObject object = new JSONObject(content);
                        mList = QaQuestionResolve.resolveHeadData(object);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new QARcAdapter(mList);
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
             }
     }
