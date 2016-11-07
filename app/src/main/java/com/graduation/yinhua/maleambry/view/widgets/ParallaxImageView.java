package com.graduation.yinhua.maleambry.view.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.ParallaxViewHolder;

/**
 * ParallaxImageView.java
 * Description: 自定义视差ImageView，控制视差动画
 *
 * Created by yinhua on 2016/11/7.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class ParallaxImageView extends ImageView {

    public ParallaxImageView(Context context) {
        this(context, null);
    }

    public ParallaxImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ParallaxImageView);

        if(typedArray != null && typedArray.length() > 0) {
            ParallaxViewHolder viewHolder = new ParallaxViewHolder();
            viewHolder.setAlphaIn(typedArray.getFloat(R.styleable.ParallaxImageView_a_in, 0f));
            viewHolder.setAlphaOut(typedArray.getFloat(R.styleable.ParallaxImageView_a_out, 0f));
            viewHolder.setxIn(typedArray.getFloat(R.styleable.ParallaxImageView_x_in, 0f));
            viewHolder.setxOut(typedArray.getFloat(R.styleable.ParallaxImageView_x_out, 0f));
            viewHolder.setyIn(typedArray.getFloat(R.styleable.ParallaxImageView_y_in, 0f));
            viewHolder.setyOut(typedArray.getFloat(R.styleable.ParallaxImageView_y_out, 0f));
            setTag(viewHolder);
        }

        typedArray.recycle();

    }
}
