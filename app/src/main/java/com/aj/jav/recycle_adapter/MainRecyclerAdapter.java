package com.aj.jav.recycle_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aj.jav.R;
import com.aj.jav.constant.Constant;
import com.aj.jav.contract.MainListContract;
import com.aj.jav.recycle_holder.AdHolder;
import com.aj.jav.recycle_holder.BaseHolder;
import com.aj.jav.recycle_holder.VideoHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by pascal
 * 主要的影片列表
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private MainListContract.Presenter mPresenter;

    /**
     * @filmCallback 操控fragment ui
     * @filmPresenter 操控網路或邏輯
     */
    public MainRecyclerAdapter(Context context, MainListContract.Presenter presenter) {
        this.mContext = context;
        this.mPresenter = presenter;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case Constant.FILM_RECYCLE_ITEM_TYPE_AD_LONG:
                return new AdHolder(mContext,
                        mPresenter,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ad_long, parent, false));

            case Constant.FILM_RECYCLE_ITEM_TYPE_AD_SHORT:
                return new AdHolder(mContext,
                        mPresenter,
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ad_short, parent, false));

            case Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_LONG_1:
                return new VideoHolder(mContext,
                        mPresenter,
                        LayoutInflater.from(parent.getContext()).inflate( R.layout.item_film_long, parent, false));

            case Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_LONG_2:
                return new VideoHolder(mContext,
                        mPresenter,
                        LayoutInflater.from(parent.getContext()).inflate( R.layout.item_film_long2, parent, false));

            case Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_SHORT_2:
                return new VideoHolder(mContext,
                        mPresenter,
                        LayoutInflater.from(parent.getContext()).inflate( R.layout.item_film_short, parent, false));

            case Constant.FILM_RECYCLE_ITEM_TYPE_NO_MORE:
                return new BaseHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_more, parent, false));

            default:
                return new ViewHolder(new View(parent.getContext()));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int listType = getItemViewType(position);

        switch (listType) {
            case Constant.FILM_RECYCLE_ITEM_TYPE_AD_LONG:
            case Constant.FILM_RECYCLE_ITEM_TYPE_AD_SHORT:
                MainListContract.AdHolderView adHolder = (AdHolder) holder;
                mPresenter.onBindAdHolderViewAtPosition(adHolder , position);
                break;

            case Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_LONG_1:
            case Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_LONG_2:
            case Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_SHORT_2:
                MainListContract.VideoHolderView videoHolder = (VideoHolder) holder;
                mPresenter.onBindVideoHolderViewAtPosition(videoHolder , position);
                break;

            case Constant.FILM_RECYCLE_ITEM_TYPE_NO_MORE:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mPresenter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getDataCount();
    }
}


