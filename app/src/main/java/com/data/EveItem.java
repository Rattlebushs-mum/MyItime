package com.data;

import java.io.Serializable;

public class EveItem implements Serializable {
    int year;
    int month;
    int day;
    int hour;
    int minu;
    int repeat;
    int drawableId;
    String imagePath;
    String title;
    String remark;
    public EveItem(){
    }
//必要信息
    public EveItem(String title,String remark,int year, int month, int day, int hour, int minu) {
        this.title=title;
        this.remark=remark;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minu = minu;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinu() {
        return minu;
    }

    public void setMinu(int minu) {
        this.minu = minu;
    }

    public int getRepeat() { return repeat; }
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
