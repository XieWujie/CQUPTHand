package com.example.xiewujie.cqupthand.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xiewujie.cqupthand.R;
import com.example.xiewujie.cqupthand.json.Lesson;

import java.util.List;

public class Curriculum_rc_adapter extends RecyclerView.Adapter<Curriculum_rc_adapter.ViewHolder> {
    private List<Lesson> lessonList;
    private String[] weekGroup = {"一","二","三","四","五","六","七","八","九","十","十一","十二"};
    public Curriculum_rc_adapter(List<Lesson> mList) {
        lessonList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.curriculum_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position<6){
            updataLesson(holder,position);
        }
    }
    private void updataLesson(ViewHolder holder,int i){
        int num = i+1;
        int numLesson = 2*i+1;
            holder.lessonNum.setText(numLesson+"\n"+"\n"+"\n"+(numLesson+1));
            holder.lessonNum.setGravity(Gravity.CENTER);
            if (lessonList!=null) {
                for (int j = 0; j < lessonList.size(); j++) {
                    Lesson lesson = lessonList.get(j);
                    if (lesson.getLesson().contains(weekGroup[numLesson])) {
                        if (lesson.getDay().contains("一")) {
                            setTextBackground(holder.monday,num);
                            setText(lesson,holder.monday);
                        }
                        if (lesson.getDay().contains("二")) {
                            setTextBackground(holder.tuesday,num);
                           setText(lesson,holder.tuesday);
                        }
                        if (lesson.getDay().contains("三")) {
                            setTextBackground(holder.wednesday,num);
                            setText(lesson,holder.wednesday);
                        }
                        if (lesson.getDay().contains("四")) {
                            setTextBackground(holder.thursday,num);
                            setText(lesson,holder.thursday);
                        } else if (lesson.getDay().contains("五")) {
                            setTextBackground(holder.friday,num);
                            setText(lesson,holder.friday);
                        }else if (lesson.getDay().contains("六")) {
                            setTextBackground(holder.saturday,num);
                            setText(lesson,holder.saturday);
                        }else if (lesson.getDay().contains("日")) {
                            setTextBackground(holder.sunday,num);
                            setText(lesson,holder.sunday);
                        }
                    }
                }
            }
        }
        private void setText(Lesson lesson,TextView textView){
        String content = lesson.getCourse();
        int length = content.length();
        if (content.contains("(")){
            length-=2;
        }
           textView.setText(lesson.getCourse());
           if (length<=3){
             textView.append("\n"+"\n"+"\n"+"\n"+lesson.getLesson());
           }else if (length>3&&length<=6){
               textView.append("\n"+"\n"+"\n"+lesson.getLesson());
           }else if (length>6&&length<=9){
               textView.append("\n"+"\n"+lesson.getLesson());
           }else {
               textView.append("\n"+lesson.getLesson());
           }
        }

        private void setTextBackground(TextView view,int num){
        if (num<=2){
            view.setBackgroundColor(Color.parseColor("#ff89a5"));
        }else if (num>2&&num<=4){
            view.setBackgroundColor(Color.parseColor("#ffbf7b"));
        }else {
            view.setBackgroundColor(Color.parseColor("#81b6fe"));
        }
        }


    @Override
    public int getItemCount() {
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private  TextView lessonNum;
        private TextView monday;
        private TextView tuesday;
        private TextView wednesday;
        private TextView thursday;
        private TextView friday;
        private TextView saturday;
        private TextView sunday;
        public ViewHolder(View lessonView) {
            super(lessonView);
             lessonNum = (TextView)lessonView.findViewById(R.id.lesson_num);
             monday = (TextView)lessonView.findViewById(R.id.monDay);
             tuesday = (TextView)lessonView.findViewById(R.id.tuesDay);
             wednesday = (TextView)lessonView.findViewById(R.id.wednesDay);
             thursday = (TextView)lessonView.findViewById(R.id.thursDay);
             friday = (TextView)lessonView.findViewById(R.id.friDay);
             saturday = (TextView)lessonView.findViewById(R.id.saturDay);
             sunday = (TextView)lessonView.findViewById(R.id.sunDay);
        }
    }
}
