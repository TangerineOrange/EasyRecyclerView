package com.cafe.view.easyrecyclerview.fragment;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cafe.demo.library.refresh.IRecyclerView;
import com.cafe.demo.library.refresh.OnRefreshListener;

/**
 * Created by cafe on 2017/5/1.
 */

/**
 * 1、 initialize the RecyclerView
 * 2、 set Adapter and Manager
 * 3、 download net Data.
 */
public abstract class BaseListFragment extends BaseFragment {

    protected IRecyclerView refreshSupport;

    abstract IRecyclerView getRefreshView(View view);

    abstract void downloadData();

    abstract RecyclerView.Adapter getAdapter();

    abstract RecyclerView.LayoutManager getLayoutManager();

    @CallSuper
    @Override
    public void initView(View view) {
        refreshSupport = getRefreshView(view);
        refreshSupport.init(getAdapter(), getLayoutManager());
        refreshSupport.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadData();
            }
        });
    }
}
