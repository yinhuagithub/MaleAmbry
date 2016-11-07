package com.graduation.yinhua.maleambry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.graduation.yinhua.maleambry.view.activity.WelcomeActivity;

/**
 * MainActivity.java
 * Description: 主界面
 *
 * Created by yinhua on 2016/11/7.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, WelcomeActivity.class));
    }
}
