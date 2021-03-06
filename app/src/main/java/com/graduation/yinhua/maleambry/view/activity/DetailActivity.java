package com.graduation.yinhua.maleambry.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.yinhua.maleambry.MaleAmbryApp;
import com.graduation.yinhua.maleambry.R;
import com.graduation.yinhua.maleambry.model.FavoDiscovery;
import com.graduation.yinhua.maleambry.model.User;
import com.graduation.yinhua.maleambry.view.base.BaseActivity;

import butterknife.BindView;

/**
 * DetailActivity.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/29.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class DetailActivity extends BaseActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.iv_fav)
    ImageView mIvFav;

    @BindView(R.id.ll_gallery)
    LinearLayout mLlGallery;

    private int did;
    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    protected boolean getImmersiveStatus() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        did = intent.getIntExtra("did", -1);
        String title = intent.getStringExtra("title");
        String thumb_url = intent.getStringExtra("thumb_url");

        if(!TextUtils.isEmpty(type) && type.equals("discovery") && did != -1) {
            mIvFav.setVisibility(View.VISIBLE);
            if(MaleAmbryApp.containsDiscovery(did)) {
                mIvFav.setSelected(true);
            } else {
                mIvFav.setSelected(false);
            }
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        mLlGallery.addView(mWebView);

        initWebView(title, thumb_url);
//        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        mLlGallery.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    @SuppressLint("NewApi")
    private void initWebView(final String toolbar_title, String thumb_url) {

        mWebSettings = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (TextUtils.isEmpty(toolbar_title)) {
                    mToolbarTitle.setText(title);
                } else {
                    mToolbarTitle.setText(toolbar_title);
                }
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setUserAgentString("User-Agent:Android");
        mWebView.loadUrl(thumb_url);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.this.finish();
            }
        });

        mIvFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = MaleAmbryApp.getUser();
                if(MaleAmbryApp.containsDiscovery(did)) {
                    FavoDiscovery.removeFavoDid(user.getApp_token(), did);
                    MaleAmbryApp.removeDiscovery(did);
                    mIvFav.setSelected(false);
                } else {
                    if(user != null && user.isLogin()) {
                        FavoDiscovery.addFavoDid(user.getApp_token(), did);
                        FavoDiscovery  favoDiscovery = new FavoDiscovery();
                        favoDiscovery.setDid(did);
                        MaleAmbryApp.getmFavoDiscoveryList().add(favoDiscovery);
                        mIvFav.setSelected(true);
                    } else {
                        Toast.makeText(DetailActivity.this, "请先登录后，再来收藏", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
//        System.exit(0);
        super.onDestroy();
    }

}
