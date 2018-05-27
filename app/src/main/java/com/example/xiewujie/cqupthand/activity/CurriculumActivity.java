package com.example.xiewujie.cqupthand.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.xiewujie.cqupthand.R;
import com.example.xiewujie.cqupthand.adapter.Curriculum_rc_adapter;
import com.example.xiewujie.cqupthand.adapter.VPWeekAdapter;
import com.example.xiewujie.cqupthand.httpUtil.HttpPostUtil;
import com.example.xiewujie.cqupthand.json.Lesson;
import com.example.xiewujie.cqupthand.json.LessonResolve;
import com.example.xiewujie.cqupthand.listener.CallbackListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CurriculumActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView weekNumText;
    private ViewPager viewPagerOfWeek;
    private VPWeekAdapter weekAdapter;
    private ViewPager curriculumPager;
    private  List<View> weekList;
    private List<View> curriculumList;
    private SharedPreferences preferences;
    private String stu_num;
    private  int firstDay = 5;
    private int m = 0;
    private String nowWeek;
    private List<Lesson> lessonList;
    private String[] weekGroup = {"一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五","十六",
            "十七","十八","十九","二十","二十一","二十二","二十三","二十四","二十五"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);
        weekNumText = (TextView)findViewById(R.id.week_number);
        initUserData();
        initPagerWeek();
        initCurruculum();
    }

    private void initUserData(){
        preferences = getSharedPreferences("userData",MODE_PRIVATE);
        stu_num = preferences.getString("stuNum",null);
        if (stu_num!=null){
            getCurriculumData();
        }
    }

    private void getCurriculumData(){
        String address = "https://wx.idsbllp.cn/api/kebiao";
        String postContent = "stu_num="+stu_num;
        HttpPostUtil.sendHttpRequest(address, postContent, new CallbackListener() {
            @Override
            public void onFinish(String response) {
                resolveData(response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void resolveData(String content){
        Log.d("curriculumActivity",content);
        try {
            JSONObject object = new JSONObject(content);
            nowWeek = object.getString("nowWeek");
            lessonList = LessonResolve.lessonsResolve(object.getJSONArray("data"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void initPagerWeek(){
        viewPagerOfWeek = (ViewPager)findViewById(R.id.view_pager_of_week);
        weekList = new ArrayList<>();
        int k = 0;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ListView.LayoutParams.MATCH_PARENT);
        for (int i = 0;i<5;i++){
            LinearLayout linearLayout = new LinearLayout(this);
            for (int j= 0;j<5;j++) {
                TextView textView = new TextView(this);
                textView.setGravity(Gravity.CENTER);
                if(i==0&&j==0){
                    TextView firstTextView = new TextView(this);
                    textView.setOnClickListener(this);
                    firstTextView.setGravity(Gravity.CENTER);
                    firstTextView.setText("整学期");
                    textView.setTag(k);
                    linearLayout.addView(firstTextView,params);
                }
                textView.setTag(k);
                textView.setText("第" + weekGroup[k] + "周");
                params.weight = 1;
                linearLayout.addView(textView, params);
                k++;
            }
            weekList.add(linearLayout);
        }
        weekAdapter = new VPWeekAdapter(weekList);
        viewPagerOfWeek.setAdapter(weekAdapter);
    }

   private void initCurruculum(){
        curriculumPager = (ViewPager)findViewById(R.id.curriculum_view_pager);
        curriculumList = new ArrayList<>();
        if (stu_num!=null) {
            for (int i = 0; i < 25; i++) {
                View dayView = LayoutInflater.from(this).inflate(R.layout.curriculum_day_item,null,false);
                updateDate(dayView,i+1);
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(dayView,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                RecyclerView recyclerView = new RecyclerView(this);
                LinearLayoutManager manager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(manager);
                if (lessonList!=null) {
                    Curriculum_rc_adapter adapter = new Curriculum_rc_adapter(lessonList);
                    recyclerView.setAdapter(adapter);
                    linearLayout.addView(recyclerView);
                    curriculumList.add(linearLayout);
                }else {
                    initCurruculum();
                }
            }
        }else {
            curriculumList.clear();
            View view = LayoutInflater.from(this).inflate(R.layout.without_curriculum_layout,null,false);
            curriculumList.add(view);
            TextView textView = (TextView)view.findViewById(R.id.without_log_in_text);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CurriculumActivity.this,LogInActivity.class);
                    startActivity(intent);
                }
            });
        }
       VPWeekAdapter adapter = new VPWeekAdapter(curriculumList);
        curriculumPager.setAdapter(adapter);
        curriculumPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                weekNumText.setText("第"+weekGroup[position]+"周");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
   }

   private void updateDate(View view,int weekNum){
        int monthNum = getMonth(weekNum);
        int firstdayOfweek = firstDay+m*7;
        TextView monday = (TextView)view.findViewById(R.id.monday);
       TextView tuesday = (TextView)view.findViewById(R.id.tuesday);
       TextView wednesday = (TextView)view.findViewById(R.id.wednesday);
       TextView thursday = (TextView)view.findViewById(R.id.thursday);
       TextView friday = (TextView)view.findViewById(R.id.friday);
       TextView saturday = (TextView)view.findViewById(R.id.saturday);
       TextView sunday = (TextView)view.findViewById(R.id.sunday);
       TextView month = (TextView)view.findViewById(R.id.month);
       if (monthNum<11) {
           month.setText("0"+monthNum + "月");
       }else {
           month.setText(monthNum+"月");
       }
       int dayNumOfMonth = 31;
       monday.setText("星期一"+"\n"+firstdayOfweek+"日");
       tuesday.setText("星期二"+"\n"+(firstdayOfweek+1)%dayNumOfMonth+"日");
       thursday.setText("星期四"+"\n"+(firstdayOfweek+3)%dayNumOfMonth+"日");
       wednesday.setText("星期三"+"\n"+(firstdayOfweek+2)%dayNumOfMonth+"日");
       friday.setText("星期五"+"\n"+(firstdayOfweek+4)%dayNumOfMonth+"日");
       saturday.setText("星期六"+"\n"+(firstdayOfweek+5)%dayNumOfMonth+"日");
       sunday.setText("星期日"+"\n"+(firstdayOfweek+6)%dayNumOfMonth+"日");
       m++;
       if (firstdayOfweek>=24){
           m=0;
       }
   }
   private int getMonth(int weekNum){
        if (weekNum>0&&weekNum<5){
            if (weekNum==1){
                firstDay = 5;
                m = 0;
            }
            return 3;
        }else if (weekNum>4&&weekNum<10){
            if (weekNum==5) {
                firstDay = 2;
                m = 0;
            }
            return 4;
        }else if (weekNum>9&&weekNum<14){
            if (weekNum==10){
                firstDay = 7;
            }
            return 5;
        }else if (weekNum>13&&weekNum<18){
            if (weekNum==14){
                firstDay = 4;
            }
            return 6;
        }else if (weekNum>17&weekNum<23){
            if (weekNum==18){
                firstDay = 2;
            }
            return 7;
        }else if (weekNum>22&&weekNum<26){
            if (weekNum==23){
                firstDay = 6;
            }
            return 8;
        }
        return 0;
   }
    @Override
    protected void onResume() {
        super.onResume();
        initUserData();
        initCurruculum();
    }

    @Override
    public void onClick(View v) {
        int k = Integer.valueOf(v.getTag().toString());
        Log.e("CurriculumActivity",k+"-----------------");
        if (curriculumPager!=null){
            curriculumPager.setCurrentItem(k);
        }
    }
}
