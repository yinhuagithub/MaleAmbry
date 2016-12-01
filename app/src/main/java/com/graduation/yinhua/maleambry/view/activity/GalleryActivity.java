package com.graduation.yinhua.maleambry.view.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.adapter.GalleryAdapter;
import com.graduation.yinhua.maleambry.model.Gallery;
import com.graduation.yinhua.maleambry.model.StatusCode;
import com.graduation.yinhua.maleambry.model.ThumbMatch;
import com.graduation.yinhua.maleambry.model.ThumbSingle;
import com.graduation.yinhua.maleambry.net.MaleAmbryRetrofit;
import com.graduation.yinhua.maleambry.net.response.ResponseMessage;
import com.graduation.yinhua.maleambry.view.base.BaseActivity;
import com.graduation.yinhua.maleambry.view.widgets.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    @BindView(R.id.viewpager)
    ViewPager mVpGallery;

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
        mVpGallery = (ViewPager) findViewById(R.id.viewpager);
        mVpGallery.setPageMargin(30);
        mVpGallery.setOffscreenPageLimit(3);
        mVpGallery.setPageTransformer(false, new ScaleTransformer());

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        mToolbarTitle.setText(intent.getStringExtra("title"));
        if(type.equals("match")) {
            fetchThumbMatchByNet(intent.getIntExtra("mid", 0));
        } else if (type.equals("single")) {
            fetchThumbSingleByNet(intent.getIntExtra("sid", 0));
        }
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

    /**
     * 获取搭配图片集
     * @param mid
     */
    private void fetchThumbMatchByNet(int mid) {
        MaleAmbryRetrofit.getInstance().getThumbMatch(mid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<List<ThumbMatch>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(ResponseMessage<List<ThumbMatch>> responseMessage) {
                        if(responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            handleThumbMatchData(responseMessage.getResults());
                        }
                    }
                });
    }

    /**
     * 处理match数据图集
     * @param results
     */
    private void handleThumbMatchData(List<ThumbMatch> results) {
        if(results != null && results.size() > 0) {
            List<Gallery> galleries = new ArrayList<Gallery>();
            for (int index = 0; index < results.size(); index++) {
                Gallery gallery = new Gallery();
                ThumbMatch thumbMatch = results.get(index);
                gallery.setThumbnail(thumbMatch.getThumbnail());
                gallery.setThumb_url(thumbMatch.getThumb_url());
                galleries.add(gallery);
            }
            GalleryAdapter adater = new GalleryAdapter(this, galleries);
            mVpGallery.setAdapter(adater);
        }
    }

    /**
     * 获取单品图片集
     * @param sid
     */
    private void fetchThumbSingleByNet(int sid) {
        MaleAmbryRetrofit.getInstance().getThumbSingle(sid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseMessage<List<ThumbSingle>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(ResponseMessage<List<ThumbSingle>> responseMessage) {
                        if(responseMessage.getStatus_code() == StatusCode.SUCCESS.getStatus_code()) {
                            handleThumbSingleData(responseMessage.getResults());
                        }
                    }
                });
    }

    /**
     * 处理match数据图集
     * @param results
     */
    private void handleThumbSingleData(List<ThumbSingle> results) {
        if(results != null && results.size() > 0) {
            List<Gallery> galleries = new ArrayList<Gallery>();
            for (int index = 0; index < results.size(); index++) {
                Gallery gallery = new Gallery();
                ThumbSingle thumbSingle = results.get(index);
                gallery.setThumbnail(thumbSingle.getThumbnail());
                gallery.setThumb_url("http://www.baidu.com");
                galleries.add(gallery);
            }
            GalleryAdapter adater = new GalleryAdapter(this, galleries);
            mVpGallery.setAdapter(adater);
        }
    }
}
