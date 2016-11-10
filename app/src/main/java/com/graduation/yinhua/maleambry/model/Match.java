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

    public static final int[] names = new int[]{R.string.xiuxian, R.string.shangwu, R.string.yundong, R.string.jianyue, R.string.fugu, R.string.yinglun, R.string.rihan, R.string.jietou};

    public static final int[] res = new int[]{R.mipmap.xiuxian, R.mipmap.shangwu, R.mipmap.yundong, R.mipmap.jianyue, R.mipmap.fugu, R.mipmap.yinglun, R.mipmap.rihan, R.mipmap.jietou};

    private int resource;
    private String name;

    public int getResource(int position) {
        return res[position];
    }

    public String getName() {
        return name;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public void setName(String name) {
        this.name = name;
    }
}
