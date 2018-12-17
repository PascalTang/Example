package com.aj.jav.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aj.jav.R;
import com.aj.jav.recycle_adapter.MainPageAdapter;

/**
 * Created by Pascal on 2018/3/20.
 * 主頁的Fragment 決定上方的Tab & 主畫面的adapter
 */
public class TabContainerFragment extends BaseTabContainerFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String[] getTab() {
        return getTabData();
    }

    /**
     * Fix Fragment not attached to Activity
     * get resource on onStart
     * <p>
     * onStart會在logicProcess後面
     * 但set tab title會導致current item = 0
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected FragmentStatePagerAdapter getPagerAdapter() {
        return new MainPageAdapter(getChildFragmentManager() , getTabData().length);
    }

    /**
     * 點擊下方Tab 要更新列表的like狀態
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }

    /**
     * 點擊進入影片內頁 返回後要更新列表的like狀態
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    private String[] getTabData() {
        return getResources().getStringArray(R.array.main_tab);
    }
}
