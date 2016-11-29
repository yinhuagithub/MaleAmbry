package com.graduation.yinhua.maleambry.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.Gallery;
import com.graduation.yinhua.maleambry.view.activity.DetailActivity;
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
    private List<Gallery> list;
    private Context context;

    public GalleryAdapter(Context context, List<Gallery> list) {
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
//                Toast.makeText(context, "hahaha---->" + list.get(position).getThumb_url(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("thumb_url", list.get(position).getThumb_url());
                context.startActivity(intent);
            }
        });
        Picasso.with(context).load(list.get(position).getThumbnail()).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
