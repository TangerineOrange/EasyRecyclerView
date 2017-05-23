package com.cafe.view.easyrecyclerview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cafe.view.easyrecyclerview.MainActivity;

/**
 * Created by cafe on 2017/5/1.
 */

public abstract class BaseFragment extends Fragment {

    protected MainActivity mContext;

    protected static Handler handler = new Handler();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void initView(View view);

}
