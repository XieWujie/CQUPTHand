package com.example.xiewujie.cqupthand.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xiewujie.cqupthand.R;
import com.example.xiewujie.cqupthand.activity.LookPicActivity;
import com.example.xiewujie.cqupthand.httpUtil.HttpPostUtil;
import com.example.xiewujie.cqupthand.listener.CallbackListener;
import com.example.xiewujie.cqupthand.tool.CircleImageView;
import com.example.xiewujie.cqupthand.json.QaHeadQuestion;
import com.example.xiewujie.cqupthand.tool.MyAppliction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QARcAdapter extends RecyclerView.Adapter<QARcAdapter.ViewHolder> {
    private List<QaHeadQuestion> mList;
    private android.os.Handler handler = new Handler();
    private Context context = MyAppliction.getContext();

    public QARcAdapter(List<QaHeadQuestion> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.q_a_home_ry_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final QaHeadQuestion question = mList.get(position);
        holder.nickNameText.setText(question.getNickname());
        if (question.getGender().equals("男")) {
            holder.genderView.setImageResource(R.drawable.boy);
        }else {
            holder.genderView.setImageResource(R.drawable.girl);
        }
        holder.labelText.setText("#"+question.getKind()+"#");
        holder.titleText.setText(question.getTitle());
        holder.descriptionText.setText(question.getDescription());
        holder.pointText.setText(question.getReward()+"积分");
        initeDetail(question,holder);
    }

    private void initeDetail(final QaHeadQuestion question,final ViewHolder holder){
        SharedPreferences preferences = context.getSharedPreferences("userData",Context.MODE_PRIVATE);
        String stuNum = preferences.getString("stuNum",null);
        String idNum = preferences.getString("idNum",null);
        String id = question.getId();
        final String postContent ="stuNum="+stuNum+"&idNum="+idNum+"&question_id="+id;
        Log.d("QARcAdapter",postContent);
        final String address = "https://wx.idsbllp.cn/springtest/cyxbsMobile/index.php/QA/Question/getDetailedInfo";
        HttpPostUtil.sendHttpRequest(address, postContent, new CallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject ob = object.getJSONObject("data");
                    JSONArray array = ob.getJSONArray("photo_urls");
                    List<String> list = new ArrayList<>();
                    for (int i = 0;i<array.length();i++){
                        list.add(array.getString(i));
                    }
                  final String[]photoUrls = new String[list.size()];
                    for (int k = 0;k<list.size();k++){
                        photoUrls[k] = list.get(k);
                        Log.d("QARcAdapter",photoUrls[k]);
                    }
                   handler.post(new Runnable() {
                       @Override
                       public void run() {
                           if (photoUrls==null||photoUrls.length==0){
                               holder.lookPicView.setVisibility(View.GONE);
                           }else {
                               holder.lookPicView.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Intent intent = new Intent();
                                       intent.setClass(context, LookPicActivity.class);
                                       intent.putExtra("pics",(String[])question.getPhoto_urls().toArray());
                                       context.startActivity(intent);
                                   }
                               });
                           }
                       }
                   });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Exception e) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView circleImageView;
        private TextView nickNameText;
        private ImageView genderView;
        private TextView labelText;
        private TextView titleText;
        private TextView descriptionText;
        private TextView lookPicView;
        private TextView pointText;
        private RelativeLayout helpLayout;
        public ViewHolder(View v) {
            super(v);
            circleImageView = (CircleImageView)v.findViewById(R.id.q_a_head_rc_view);
            nickNameText = (TextView) v.findViewById(R.id.q_a_user_nic_name);
            genderView = (ImageView)v.findViewById(R.id.q_a_gender);
            labelText = (TextView)v.findViewById(R.id.q_a_label);
            titleText = (TextView)v.findViewById(R.id.q_a_head_title);
            descriptionText =  (TextView)v.findViewById(R.id.q_a_detail);
            lookPicView = (TextView)v.findViewById(R.id.q_a_look_picture);
            helpLayout = (RelativeLayout)v.findViewById(R.id.q_a_help_layout);
            pointText = (TextView)v.findViewById(R.id.q_a_point);
        }
    }

}
