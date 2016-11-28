package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.graduation.yinhua.maleambry.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * GalleryAdapter.java
 * Description:
 * <p>
 * Created by yinhua on 2016/11/28.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class GalleryAdapter extends PagerAdapter {
    private List<String> list;
    private Context context;

    public GalleryAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_gallery);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "hahaha" + position, Toast.LENGTH_SHORT).show();
            }
        });
        Picasso.with(context).load(list.get(position)).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
