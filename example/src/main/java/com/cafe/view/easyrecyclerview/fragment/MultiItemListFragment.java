package com.cafe.view.easyrecyclerview.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.cafe.demo.library.RefreshRecyclerView;
import com.cafe.demo.library.adapter.MultiItemRecyclerAdapter;
import com.cafe.demo.library.adapter.MultiItemTypeSupport;
import com.cafe.demo.library.adapter.ViewHolder;
import com.cafe.view.easyrecyclerview.DataType;
import com.cafe.view.easyrecyclerview.DataUtil;
import com.cafe.view.easyrecyclerview.R;

import java.util.ArrayList;

/**
 * Created by cafe on 2017/5/1.
 */

public class MultiItemListFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_example;
    }


    @Override
    public void initView(View view) {
        initRecyclerView(view);

    }


    private void initRecyclerView(View view) {

        RefreshRecyclerView recyclerView = (RefreshRecyclerView) view.findViewById(R.id.id_refreshRecyclerView);

        ArrayList<DataType> data = new ArrayList<>();

        MultiItemRecyclerAdapter<DataType> adapter = new MultiItemRecyclerAdapter<DataType>(mContext, data, new MultiItemTypeSupport<DataType>() {
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == 0) {
                    return R.layout.listview_a;
                } else if (itemType == 1) {
                    return R.layout.listview_b;
                } else {
                    return R.layout.listview_c;
                }
            }

            @Override
            public int getItemViewType(int position, DataType dataType) {
                if (position % 10 == 0) {
                    return 2;
                } else if (position % 5 == 0) {
                    return 1;
                }
                return 0;
            }
        }) {
            @Override
            protected void onBind(ViewHolder holder, DataType dataType, int position) {
                int itemType = getItemViewType(position);
                Log.i("getView", "itemType " + itemType);
                Log.i("getView", "getItemViewType " + holder.getItemViewType());
                Log.i("getView", "getItemView " + holder.itemView);
                if (itemType == 0) {
                    holder.setText(R.id.id_summary, position + "");
                } else if (itemType == 1) {
                    holder.setText(R.id.id_num, position + "");
                }
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        data.addAll(DataUtil.getListData(20));
        adapter.notifyDataSetChanged();


    }
}
