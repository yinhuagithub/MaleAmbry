package com.graduation.yinhua.maleambry.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.MatchStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * MatchStyleUtil.java
 * Description:
 * <p>
 * Created by yinhua on 2016/11/11.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MatchStyleUtil {

    private static final int[] names = new int[]{R.string.xiuxian, R.string.shangwu, R.string.yundong, R.string.jianyue, R.string.fugu, R.string.yinglun, R.string.rihan, R.string.jietou};

    private static final int[] res = new int[]{R.mipmap.xiuxian, R.mipmap.shangwu, R.mipmap.yundong, R.mipmap.jianyue, R.mipmap.fugu, R.mipmap.yinglun, R.mipmap.rihan, R.mipmap.jietou};

    /**
     * 加载本地MatchStyle数据
     * @param context
     * @return
     */
    public static List<MatchStyle> loadLocalMatchStyleData(Context context) {
        List<MatchStyle> list = new ArrayList<MatchStyle>();

        Resources resources = context.getResources();
        for (int index = 0; index < names.length; index++) {
            MatchStyle style = new MatchStyle();
            style.setRes(res[index]);
            style.setStyleName(resources.getString(names[index]));
            list.add(style);
        }
        return list;
    }
}
