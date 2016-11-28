package com.graduation.yinhua.maleambry.view.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.GalleryAdapter;
import com.graduation.yinhua.maleambry.view.base.BaseActivity;
import com.graduation.yinhua.maleambry.view.widgets.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * GalleryActivity.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/29.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class GalleryActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @Override
    protected boolean getImmersiveStatus() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setPageMargin(30);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(false, new ScaleTransformer());
        List<String> list = new ArrayList<>();
        list.add("https://gd4.alicdn.com/imgextra/i1/2562423560/TB2oi7rap15V1Bjy1XbXXaNcVXa_!!2562423560.jpg");
        list.add("https://gd4.alicdn.com/imgextra/i4/55139032/TB2ZPkKhFXXXXa8XpXXXXXXXXXX_!!55139032.jpg");
        list.add("https://img.alicdn.com/bao/uploaded/i1/TB1OHxQHXXXXXXBXXXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");
        list.add("https://img.alicdn.com/imgextra/i1/1693687552/TB2DPYkaNvzQeBjSZPfXXbWGFXa_!!1693687552.jpg_430x430q90.jpg");
        list.add("https://gd4.alicdn.com/imgextra/i1/92867844/TB2XMSjangc61BjSZFzXXXH2FXa_!!92867844.jpg");
        list.add("https://img.alicdn.com/bao/uploaded/i1/TB115dBOXXXXXXlXFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");
        GalleryAdapter adater = new GalleryAdapter(this, list);
        viewPager.setAdapter(adater);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.this.finish();
            }
        });
    }
}
