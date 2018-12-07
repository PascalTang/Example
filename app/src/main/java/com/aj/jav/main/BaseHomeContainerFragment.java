package com.aj.jav.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aj.jav.R;

/**
 * Created by chris on 2017/12/7.
 * 首頁的公用承載頁
 */

public abstract class BaseHomeContainerFragment extends Fragment {
    protected static final String TYPE = "type";
    protected static final String PARAM_SEARCH = "search_result";
    protected boolean mIsSearchResult;

    protected TabLayout mTabLayout;
    protected ViewPager mViewPager;

    protected int mTabCounter;
    protected String[] mTabTitle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mIsSearchResult = getArguments().getBoolean(PARAM_SEARCH);
        }
//        ViewUtility.setMargins(mTabLayout, 0, ValueUtility.getStatusBarHeight(getActivity()), 0, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate( R.layout.fragment_tab_container, container, false);
        findViews(view, savedInstanceState);
        return view;
    }

    private void findViews(View view, Bundle savedInstanceState) {
        mTabLayout = view.findViewById(R.id.tab);
        mViewPager = view.findViewById(R.id.viewpager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setViewPager();
    }

//    @Override
//    protected void initData() {
//        mListener.changeStatusBarColor(Constant.RED_STATUS_BAR);
//    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        mListener.changeStatusBarColor(Constant.RED_STATUS_BAR);
//    }


    @Override
    public void onStart() {
        super.onStart();
        if (mTabLayout.getTabCount() != 0) return;
        setTabTitle();
        setTabLayout();
    }

    protected abstract PagerAdapter getPagerAdapter();

    /**
     * 設定ViewPager
     */
    private void setViewPager() {
        if (getPagerAdapter()==null) Log.i("ddd","1");
        if (mViewPager==null) Log.i("ddd","2");
        mViewPager.setAdapter(getPagerAdapter());
        mViewPager.addOnPageChangeListener(pageChangeListener);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(5);
    }

    protected abstract void setTabTitle();

    /**
     * 設定TabLayout
     */
    private void setTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mTabLayout.addOnTabSelectedListener(getTabSelectedListener());

        for (int i = 0; i < mTabCounter; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[i]));
        }
    }

    protected abstract TabLayout.OnTabSelectedListener getTabSelectedListener();

    /**
     * ViewPager 滑動時的監聽
     */
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            TabLayout.Tab tab = mTabLayout.getTabAt(position);
            if (mTabLayout.getSelectedTabPosition() != position && tab != null) {
                tab.select();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
