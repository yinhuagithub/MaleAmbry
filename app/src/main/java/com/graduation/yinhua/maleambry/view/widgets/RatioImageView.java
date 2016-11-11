package com.graduation.yinhua.maleambry.view.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.graduation.yinhua.maleambry.R;

/**
 * RatioImageView.java
 * Description:根据宽高比例ImageView
 * <p/>
 * Created by yinhua on 2016/11/10.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class RatioImageView extends ImageView {

    private float originWidth;
    private float originHeight;

    public RatioImageView(Context context) {
        this(context, null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        originWidth = ta.getFloat(R.styleable.RatioImageView_origin_width, 0f);
        originHeight = ta.getFloat(R.styleable.RatioImageView_origin_height, 0f);
        ta.recycle();
    }

    public void setOriginSize(float originWidth, float originHeight) {
        this.originWidth = originWidth;
        this.originHeight = originHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (originHeight >0 && originWidth >0) {
            float ratio = originWidth / originHeight;

            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);

            if (width > 0) {
                height = (int) ((float)width / ratio);
            }
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
