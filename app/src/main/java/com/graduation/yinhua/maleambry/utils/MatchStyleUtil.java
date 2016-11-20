package com.graduation.yinhua.maleambry.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.MatchStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * MatchStyleUtil.java
 * Description: 加载风格资源工具类
 * <p>
 * Created by yinhua on 2016/11/11.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MatchStyleUtil {

    private static final String TAG = MatchStyleUtil.class.getSimpleName();

    private static final int[] MATCH_NAMES = new int[]{R.string.xiuxian, R.string.shangwu, R.string.yundong, R.string.jianyue, R.string.fugu, R.string.yinglun, R.string.rihan, R.string.jietou};
    private static final int[] SINGLE_TOPS_NAMES = new int[]{R.string.single_tops_jacket, R.string.single_tops_fleece, R.string.single_tops_sweater, R.string.single_tops_down,
                                                        R.string.single_tops_sport, R.string.single_tops_wind, R.string.single_tops_long_sleeved, R.string.single_tops_long_sleeve,
                                                        R.string.single_tops_chaqueta, R.string.single_tops_short_sleeve, R.string.single_tops_short_sleeved};
    private static final int[] SINGLE_PANTS_NAMES = new int[]{R.string.single_pants_casual, R.string.single_pants_jeans, R.string.single_pants_sport, R.string.single_pants_pencil,
                                                            R.string.single_pants_harem, R.string.single_pants_cropped, R.string.single_pants_short};
    private static final int[] SINGLE_SHOES_NAMES = new int[]{R.string.single_shoes_board, R.string.single_shoes_canvas, R.string.single_shoes_sport, R.string.single_shoes_doug,
                                                            R.string.single_shoes_high, R.string.single_shoes_ugg};
    private static final int[] SINGLE_ACCESSORY_NAMES = new int[]{R.string.single_accessory_watch, R.string.single_accessory_sunglasses, R.string.single_accessory_belt, R.string.single_accessory_bracelet,
                                                                R.string.single_accessory_cap, R.string.single_accessory_tie};

    private static final int[] MATCH_RESOURCE = new int[]{R.mipmap.xiuxian, R.mipmap.shangwu, R.mipmap.yundong, R.mipmap.jianyue, R.mipmap.fugu, R.mipmap.yinglun, R.mipmap.rihan, R.mipmap.jietou};
    private static final int[] SINGLE_TOPS_RESOURCE = new int[]{R.mipmap.tops_jacket, R.mipmap.tops_fleece, R.mipmap.tops_sweater, R.mipmap.tops_down,
                                                            R.mipmap.tops_sport, R.mipmap.tops_wind, R.mipmap.tops_long_sleeved, R.mipmap.tops_long_sleeve,
                                                            R.mipmap.tops_chaqueta, R.mipmap.tops_short_sleeve, R.mipmap.tops_short_sleeved};
    private static final int[] SINGLE_PANTS_RESOURCE = new int[]{R.mipmap.pants_casual, R.mipmap.pants_jeans, R.mipmap.pants_sport, R.mipmap.pants_pencil,
                                                                R.mipmap.pants_harem, R.mipmap.pants_cropped, R.mipmap.pants_short};
    private static final int[] SINGLE_SHOES_RESOURCE = new int[]{R.mipmap.shoes_board, R.mipmap.shoes_canvas, R.mipmap.shoes_sport, R.mipmap.shoes_doug,
                                                                R.mipmap.shoes_high, R.mipmap.shoes_ugg};
    private static final int[] SINGLE_ACCESSORY_RESOURCE = new int[]{R.mipmap.accessory_watch, R.mipmap.accessory_sunglasses, R.mipmap.accessory_belt, R.mipmap.accessory_bracelet,
                                                                R.mipmap.accessory_cap, R.mipmap.accessory_tie};
    /**
     * 加载本地MatchStyle数据
     * @param context
     * @return
     */
    public static List<MatchStyle> loadLocalMatchStyleData(Context context) {
        return loadStyleData(context, MATCH_NAMES, MATCH_RESOURCE);
    }

    /**
     * 加载本地SingleStyle数据
     * @param context
     * @param page
     * @return
     */
    public static List<MatchStyle> loadLocalSingleStyleData(Context context, int page) {
        int[] style_name = null;
        int[] style_res = null;
        switch (page) {
            case 0:{
                style_name = SINGLE_TOPS_NAMES;
                style_res = SINGLE_TOPS_RESOURCE;
                break;
            }
            case 1: {
                style_name = SINGLE_PANTS_NAMES;
                style_res = SINGLE_PANTS_RESOURCE;
                break;
            }
            case 2: {
                style_name = SINGLE_SHOES_NAMES;
                style_res = SINGLE_SHOES_RESOURCE;
                break;
            }
            case 3: {
                style_name = SINGLE_ACCESSORY_NAMES;
                style_res = SINGLE_ACCESSORY_RESOURCE;
                break;
            }
            default: {
                Log.i(TAG, "Method loadLocalSingleStyleData's args index out of range.Args is " + page);
            }
        }
        return loadStyleData(context,style_name, style_res);
    }

    /**
     * 加载本地资源数据
     * @param context
     * @param style_name
     * @param style_res
     * @return
     */
    private static List<MatchStyle> loadStyleData(Context context, int[] style_name, int[] style_res) {
        List<MatchStyle> list = new ArrayList<MatchStyle>();

        Resources resources = context.getResources();
        for (int index = 0; index < style_name.length; index++) {
            MatchStyle style = new MatchStyle();
            style.setRes(style_res[index]);
            style.setStyleName(resources.getString(style_name[index]));
            list.add(style);
        }
        return list;
    }

}
