package com.graduation.yinhua.maleambry.view.widgets;

import android.os.Handler;
import android.os.Message;

/**
 * BannerTimerController.java
 * Description: 广告定时控制器,用于执行定时任务
 *
 * Created by yinhua on 2016/11/13.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public abstract class BannerTimerController {

    private static final long INTERVAL_INVALID = 0;
    private static final int MSG = 1000;

    private long mInterval = INTERVAL_INVALID;
    private boolean mCancelled = true;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (BannerTimerController.this) {
                if(mCancelled) return;
                if (mInterval <= 0) return;
                onTick();
                sendMessageDelayed(obtainMessage(MSG), mInterval);
            }
        }
    };


    public BannerTimerController(long interval) {
        mInterval = interval;
    }

    public synchronized void start() {
        if (!mCancelled) {
            return;
        }
        if (mInterval <= INTERVAL_INVALID) {
            return;
        }
        mCancelled = false;
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG), mInterval);
    }

    public synchronized void cancel() {
        mCancelled = true;
        mInterval = INTERVAL_INVALID;
        mHandler.removeMessages(MSG);
    }

    public boolean isCancelled() {
        return mCancelled;
    }

    protected abstract void onTick();
}
