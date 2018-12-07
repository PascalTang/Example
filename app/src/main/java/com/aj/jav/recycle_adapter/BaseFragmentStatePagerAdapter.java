package com.aj.jav.recycle_adapter;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * Created by pascal on 2018/5/10.
 * Android ViewPaper使用FragmentPagerAdapter出现的FragmentManagerImpl为空的问题
 * https://blog.csdn.net/wxl901018/article/details/52920353
 */

public abstract class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    public BaseFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        try {
            super.setPrimaryItem(container, position, object);
        } catch (Exception e) {}
    }

    // try fix onRestoreInstanceState FragmentStatePagerAdapter.getItem null pointer
    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
//        super.restoreState(state, loader);
    }
}
