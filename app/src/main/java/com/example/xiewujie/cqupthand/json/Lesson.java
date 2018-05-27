package com.example.xiewujie.cqupthand.json;

public class Lesson {
    private String begin_lesson;
    private String day;
    private String lesson;
    private String weekBegin;
    private String weekEnd;
    private String classroom;
    private String teacher;
    private String course;
    private String rawWeek;
    private String type;

    public String getBegin_lesson() {
        return begin_lesson;
    }

    public void setBegin_lesson(String begin_lesson) {
        this.begin_lesson = begin_lesson;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getWeekBegin() {
        return weekBegin;
    }

    public void setWeekBegin(String weekBegin) {
        this.weekBegin = weekBegin;
    }

    public String getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(String weekEnd) {
        this.weekEnd = weekEnd;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getRawWeek() {
        return rawWeek;
    }

    public void setRawWeek(String rawWeek) {
        this.rawWeek = rawWeek;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "lesson"+lesson;
    }
}
