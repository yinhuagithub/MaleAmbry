package com.graduation.yinhua.maleambry.model;

import com.graduation.yinhua.maleambry.R;

/**
 * Match.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/10.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class Match {

    private int thumb1;
    private int thumb2;
    private int thumb3;
    private String title;
    private String descrption;

    public int getThumb1() {
        return thumb1;
    }

    public int getThumb2() {
        return thumb2;
    }

    public int getThumb3() {
        return thumb3;
    }

    public String getTitle() {
        return title;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setThumb1(int thumb) {
        this.thumb1 = thumb;
    }

    public void setThumb2(int thumb) {
        this.thumb2 = thumb;
    }

    public void setThumb3(int thumb) {
        this.thumb3 = thumb;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }
}
