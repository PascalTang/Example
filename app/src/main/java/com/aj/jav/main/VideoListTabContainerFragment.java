package com.aj.jav.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aj.jav.data_model.MenuTitleGson;
import com.aj.jav.recycle_adapter.LongVideoPagerAdapter;
import com.aj.jav.utils.ValueUtility;
import com.google.gson.Gson;

/**
 * Created by Pascal on 2018/3/20.
 * 主頁的Fragment 決定上方的Tab & 主畫面的adapter
 */
public class VideoListTabContainerFragment extends BaseHomeContainerFragment {
    public static final String TYPE_LONG = "TYPE_LONG";
    public static final String TYPE_SHORT = "TYPE_SHORT";
    public static final String TYPE_CATEGORY = "TYPE_CATEGORY";
    public static final String TYPE_FAVORITE = "TYPE_FAVORITE";
    private MenuTitleGson mMenuTitleGson = null;
    private LongVideoPagerAdapter mLongVideoPagerAdapter;
    private boolean mFirstIntoPage = true;
    private int mLastGAPosition = 0;
    private boolean mFirstTimeClickFavorite = true;

    public static VideoListTabContainerFragment newInstance() {
        VideoListTabContainerFragment fragment = new VideoListTabContainerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String menuTitle = "{\"response\":{\"menus\":{\"long\":[{\"menu_id\":0,\"menu_name\":\"最新\"},{\"menu_id\":0,\"menu_name\":\"排行\"},{\"menu_id\":2,\"menu_name\":\"无码\"},{\"menu_id\":9,\"menu_name\":\"独家\"},{\"menu_id\":10,\"menu_name\":\"中文\"}],\"short\":[{\"menu_id\":0,\"menu_name\":\"最新\"},{\"menu_id\":0,\"menu_name\":\"排行\"},{\"menu_id\":6,\"menu_name\":\"自拍\"},{\"menu_id\":7,\"menu_name\":\"偷拍\"},{\"menu_id\":11,\"menu_name\":\"无码\"}]}},\"status\":{\"code\":200,\"message\":\"success\"}}";
        mMenuTitleGson = new Gson().fromJson(menuTitle, MenuTitleGson.class);
//        newAdapter();
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

    private void newAdapter() {
        mLongVideoPagerAdapter = new LongVideoPagerAdapter(getChildFragmentManager(), mMenuTitleGson);
    }

    @Override
    protected FragmentStatePagerAdapter getPagerAdapter() {
        return new LongVideoPagerAdapter(getChildFragmentManager(), mMenuTitleGson);
    }

    @Override
    protected void setTabTitle() {
        setTab(getLongData());
    }

    /**
     * 點擊下方Tab 要更新列表的like狀態
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            updateLike();
//            updateFavoriteLike();
        }
    }

    /**
     * 點擊進入影片內頁 返回後要更新列表的like狀態
     */
    @Override
    public void onResume() {
        super.onResume();
        updateLike();
//        updateFavoriteLike();
        if (mFirstIntoPage) mFirstIntoPage = false;
    }

    public void updateLike() {
        if (mFirstIntoPage) {
            return;
        }
//        switch (mType) {
//            case TYPE_LONG:
//                if (mLongPagerAdapter != null) mLongPagerAdapter.updateAllPageLikeStatus();
//                break;
//            case TYPE_SHORT:
//                if (mShortPagerAdapter != null) mShortPagerAdapter.updateAllPageLikeStatus();
//                break;
//            case TYPE_CATEGORY:
//                if (mLongVideoPagerAdapter != null) mLongVideoPagerAdapter.updateLikeStatus();
//                break;
//        }
    }

    /**
     * Tab select時不用更新 獨立出來
     */
//    public void updateFavoriteLike() {
//        if (mFavoritePagerAdapter != null && mType.equals(TYPE_FAVORITE)) {
//            mFavoritePagerAdapter.reloadFavoriteData();
//        }
//    }

    /**
     * 點擊上方Tab 要更新列表的like狀態
     */
    @Override
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

    private String[] getLongData() {
        if (mMenuTitleGson == null) return new String[]{};
        String[] arr = new String[mMenuTitleGson.getResponse().getMenus().getLongX().size()];
        for (int i = 0; i < arr.length; i++) {
            if (!ValueUtility.isCN()) {
                arr[i] = transparentString(mMenuTitleGson.getResponse().getMenus().getLongX().get(i).getMenu_name());
            } else {
                arr[i] = mMenuTitleGson.getResponse().getMenus().getLongX().get(i).getMenu_name();
            }
        }
        return arr;
    }

    private String[] getShortData() {
        if (mMenuTitleGson == null) return new String[]{};

        String[] arr = new String[mMenuTitleGson.getResponse().getMenus().getShortX().size()];
        for (int i = 0; i < arr.length; i++) {
            if (!ValueUtility.isCN()) {
                arr[i] = transparentString(mMenuTitleGson.getResponse().getMenus().getShortX().get(i).getMenu_name());
            } else {
                arr[i] = mMenuTitleGson.getResponse().getMenus().getShortX().get(i).getMenu_name();
            }
        }
        return arr;
    }

    private String transparentString(String s) {
        switch (s) {
            case "无码":
                return "無碼";
            case "独家":
                return "獨家";
            default:
                return s;
        }
    }

    private void setTab(String[] strings) {
        if (strings == null) return;
        mTabCounter = strings.length;
        mTabTitle = strings;
    }
}
