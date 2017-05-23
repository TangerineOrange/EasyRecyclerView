package com.cafe.view.easyrecyclerview.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cafe.view.easyrecyclerview.R;

/**
 * Created by cafe on 2017/5/1.
 */

public class NavigationFragment extends BaseFragment {


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("click", "click  ");
            switch (v.getId()) {
                case R.id.btn_simpleRecyclerView:
                    mContext.addFragment(new SimpleListFragment());

                    break;
                case R.id.btn_multiItemRecyclerView:
                    mContext.addFragment(new MultiItemListFragment());

                    break;
                case R.id.button3:
                    mContext.addFragment(new MultiItemListFragment());

                    break;

            }
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    public void initView(View view) {
        final Button btnSimple = (Button) view.findViewById(R.id.btn_simpleRecyclerView);
        final Button btnMultiItem = (Button) view.findViewById(R.id.btn_multiItemRecyclerView);
        final Button btn3 = (Button) view.findViewById(R.id.button3);
        btnSimple.setOnClickListener(listener);
        btnMultiItem.setOnClickListener(listener);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("navigation", " onresume");
        mContext.showFAB();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("navigation", " onStop");

    }
}
