package com.aj.jav.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aj.jav.R;

public abstract class BaseTabContainerFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] mTabTitle;

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
    }

    private void initTabData(){
        mTabTitle = getTab();
    }

    protected abstract String[] getTab();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_tab_container, container, false);
        findViews(view, savedInstanceState);
        return view;
    }

    private void findViews(View view, Bundle savedInstanceState) {
        mTabLayout = view.findViewById(R.id.tab);
        mViewPager = view.findViewById(R.id.view_pager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initTabData();

        setTabLayout();

        setViewPager();
    }

    protected abstract PagerAdapter getPagerAdapter();

    /**
     * 設定ViewPager
     */
    private void setViewPager() {
        mViewPager.setAdapter(getPagerAdapter());
        mViewPager.addOnPageChangeListener(pageChangeListener);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(mTabTitle.length);
    }

    /**
     * 設定TabLayout
     */
    private void setTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mTabLayout.addOnTabSelectedListener(getTabSelectedListener());

        for (int i = 0; i < mTabTitle.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitle[i]));
        }
    }

    protected TabLayout.OnTabSelectedListener getTabSelectedListener() {
        return new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mViewPager != null && tab != null) {
                    mViewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

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
