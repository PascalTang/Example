package com.aj.jav.recycle_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.aj.jav.data_model.MenuTitleGson;
import java.util.LinkedHashSet;

/**
 * Created by chris on 2017/12/8.
 * 主畫面的長片Tab ViewPager Adapter
 * mFilmType == Constant.DISPLAY_TYPE_LONG_2 return 長片data ,不然就 return 短片data
 * 修改參考 https://stackoverflow.com/questions/14035090/how-to-get-existing-fragments-when-using-fragmentpageradapter
 */

public class LongVideoPagerAdapter extends BaseFragmentStatePagerAdapter {
    private LinkedHashSet<Fragment> mSet = new LinkedHashSet<>();
    private MenuTitleGson mMenuDataGson;
    public LongVideoPagerAdapter(FragmentManager fragmentManager, MenuTitleGson menuTitleGson) {
        super(fragmentManager);
        this.mMenuDataGson = menuTitleGson;
    }

    private String getMenuId(int position) {
        return String.valueOf(mMenuDataGson.getResponse().getMenus().getLongX().get(position).getMenu_id());
    }

    private String getMenuTitle(int position) {
        return String.valueOf(mMenuDataGson.getResponse().getMenus().getLongX().get(position).getMenu_name());
    }

    @Override
    public int getCount() {
        return mMenuDataGson.getResponse().getMenus().getLongX().size();
    }

    @Override
    public Fragment getItem(int position) {
        return new Fragment();
//        return CategoryFragment.newInstance(mFilmType, position, getMenuId(position), getMenuTitle(position));
    }

    public void updateAllPageLikeStatus() {
        for (Fragment f : mSet) {
//            if (f != null) ((CategoryFragment) f).updateLikeStatus();
        }
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        CategoryFragment createdFragment = (CategoryFragment) super.instantiateItem(container, position);
//        mSet.add(createdFragment);
//        return createdFragment;
//    }
}
