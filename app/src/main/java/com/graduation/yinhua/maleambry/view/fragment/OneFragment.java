package com.graduation.yinhua.maleambry.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.yinhua.maleambry.R;

/**
 * OneFragment.java
 * Description:
 * <p/>
 * Created by yinhua on 2016/11/9.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class OneFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
