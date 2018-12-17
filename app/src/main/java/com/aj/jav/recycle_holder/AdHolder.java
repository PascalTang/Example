package com.aj.jav.recycle_holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aj.jav.R;
import com.aj.jav.contract.MainListContract;
import com.aj.jav.data_model.AdGson;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class AdHolder extends RecyclerView.ViewHolder implements MainListContract.AdHolderView, View.OnClickListener {

    private MainListContract.Presenter mPresenter;
    private Context mContext;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public AdHolder(Context context, MainListContract.Presenter presenter, View itemView) {
        super(itemView);
        this.mContext = context;
        this.mPresenter = presenter;

        this.mTabLayout = itemView.findViewById(R.id.tab_dots);
        this.mViewPager = itemView.findViewById(R.id.view_pager);
        mTabLayout.setupWithViewPager(mViewPager, true);
    }

    @Override
    public void setAD(List<AdGson.ResponseBean.LongBean> adData) {
        setViewPager(adData);
    }

    private void setViewPager(List<AdGson.ResponseBean.LongBean> adData) {
        mViewPager.setAdapter(new SamplePagerAdapter(adData));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setCurrentItem(0);
    }

    private class SamplePagerAdapter extends PagerAdapter {
        private List<AdGson.ResponseBean.LongBean> mData;
        private SamplePagerAdapter(List<AdGson.ResponseBean.LongBean> data){
            this.mData = data;
        }
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return o == view;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.MATCH_PARENT  ));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(mContext).load(mData.get(position).getAd_img_url()).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)).into(iv);

            container.addView(iv);
            return iv;
        }
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

    }

    @Override
    public void onClick(View view) {
        mPresenter.onClcikAdHolder(this, getAdapterPosition());
    }

    @Override
    public void gotoBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        mContext.startActivity(browserIntent);
    }
}