package com.example.xiewujie.cqupthand.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LessonResolve {
    public static List<Lesson> lessonsResolve(JSONArray array)throws Exception{
        List<Lesson> lessonList = new ArrayList<>();
        for (int i = 0;i<array.length();i++){
            JSONObject object = array.getJSONObject(i);
            Lesson lesson = new Lesson();
            lesson.setClassroom(object.getString("classroom"));
            lesson.setCourse(object.getString("course"));
            lesson.setDay(object.getString("day"));
            lesson.setTeacher(object.getString("teacher"));
            lesson.setLesson(object.getString("lesson"));
            lesson.setRawWeek(object.getString("rawWeek"));
            lessonList.add(lesson);

        }
        return lessonList;
    }
}
