package com.aj.jav.recycle_adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.aj.jav.constant.Constant;
import com.aj.jav.main.VideoListFragment;

import java.util.LinkedHashSet;

import javax.annotation.Nonnull;

/**
 * 修改參考 https://stackoverflow.com/questions/14035090/how-to-get-existing-fragments-when-using-fragmentpageradapter
 */

public class MainPageAdapter extends BaseFragmentStatePagerAdapter {
    private LinkedHashSet<Fragment> mSet = new LinkedHashSet<>();
    private final int mSize;

    public MainPageAdapter(FragmentManager fragmentManager, int size) {
        super(fragmentManager);
        this.mSize = size;
    }

    @Override
    public int getCount() {
        return mSize;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment();
            case 1:
                return VideoListFragment.newInstance(Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_LONG_1, "0", "最新", 0);
            case 2:
                return VideoListFragment.newInstance(Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_LONG_1, "0", "最新", 0);
            case 3:
                return new Fragment();
            default:
                return new Fragment();
        }
    }

    public void updateAllPageLikeStatus() {
        for (Fragment f : mSet) {
//            if (f != null) ((CategoryFragment) f).updateLikeStatus();
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@Nonnull ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        mSet.add(createdFragment);
        return createdFragment;
    }
}
