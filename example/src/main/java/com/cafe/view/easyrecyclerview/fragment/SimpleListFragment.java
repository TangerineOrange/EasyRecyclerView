package com.cafe.view.easyrecyclerview.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cafe.demo.library.adapter.SimpleRecyclerAdapter;
import com.cafe.demo.library.adapter.ViewHolder;
import com.cafe.demo.library.refresh.IRecyclerView;
import com.cafe.view.easyrecyclerview.DataType;
import com.cafe.view.easyrecyclerview.DataUtil;
import com.cafe.view.easyrecyclerview.R;

import java.util.ArrayList;

/**
 * Created by cafe on 2017/5/1.
 */

public class SimpleListFragment extends BaseListFragment {

    private ArrayList<DataType> list = new ArrayList<>();

    private int refreshTime = 1;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_example;
    }

    private void downloadData(final IRecyclerView recyclerView) {
        if (refreshTime == 1)
            list.addAll(DataUtil.getListData(5));
        else
            list.add(0, DataUtil.getOneData(list.get(0).getA(), false));

        refreshTime++;
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                         recyclerView.completeRefresh();
                    }
                }, 1000);
            }
        }).start();
    }


    @Override
    IRecyclerView getRefreshView(View view) {
        return (IRecyclerView) view.findViewById(R.id.id_refreshRecyclerView);
    }

    @Override
    public void downloadData() {
        downloadData(refreshSupport);
    }

    @Override
    RecyclerView.Adapter getAdapter() {
        return new SimpleRecyclerAdapter<DataType>(mContext, R.layout.listview_a, list) {

            @Override
            protected void onBind(ViewHolder holder, DataType data, int position) {
                holder.setText(R.id.id_summary, data.getA() + "");
            }
        };
    }

    @Override
    RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }
}
