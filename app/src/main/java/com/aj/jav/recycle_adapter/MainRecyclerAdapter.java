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
    private List<Map<String, Object>> mDataList;
    private int mFilmType;
    private MainListContract.Presenter mPresenter;

    /**
     * @filmCallback 操控fragment ui
     * @filmPresenter 操控網路或邏輯
     */
    public MainRecyclerAdapter(Context context, List<Map<String, Object>> dataList, int filmType , MainListContract.Presenter presenter) {
        this.mContext = context;
        this.mDataList = dataList;
        this.mFilmType = filmType;
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
            case Constant.FILM_RECYCLE_ITEM_TYPE_AD:
                return new AdHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(getAdLayout(), parent, false));

            case Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_LIST:
                return new VideoHolder(mContext,
                        LayoutInflater.from(parent.getContext()).inflate(getVideoListItemLayout(), parent, false) ,
                        mFilmType);

            case Constant.FILM_RECYCLE_ITEM_TYPE_NO_MORE:
                return new BaseHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_more, parent, false));

            default:
                return new ViewHolder(new View(parent.getContext()));
        }
    }

    private int getVideoListItemLayout(){
        int itemLayoutId;
        switch (mFilmType) {
            case Constant.DISPLAY_TYPE_LONG_1:
                itemLayoutId = R.layout.item_film_long;
                break;
            case Constant.DISPLAY_TYPE_SHORT_2:
                itemLayoutId = R.layout.item_film_short;
                break;
            case Constant.DISPLAY_TYPE_LONG_2:
                itemLayoutId = R.layout.item_film_long2;
                break;
            default:
                itemLayoutId = R.layout.item_film_short;
                break;
        }
        return itemLayoutId;
    }

    private int getAdLayout(){
        switch (mFilmType) {
            case Constant.DISPLAY_TYPE_LONG_1:
                return R.layout.item_ad_long;
            case Constant.DISPLAY_TYPE_SHORT_2:
                return R.layout.item_ad_short;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int listType = getItemViewType(position);

        switch (listType) {
            case Constant.FILM_RECYCLE_ITEM_TYPE_AD:
                AdHolder adHolder = (AdHolder) holder;
                adHolder.onBindViewHolder(adHolder , position, mDataList);
                break;

            case Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_LIST:
//                VideoHolder videoHolder = (VideoHolder) holder;
//                mPresenter.onBindRepositoryRowViewAtPosition(videoHolder , position);

                VideoHolder videoHolder = (VideoHolder) holder;
                videoHolder.onBindViewHolder(videoHolder , position , mDataList);
                break;

            case Constant.FILM_RECYCLE_ITEM_TYPE_NO_MORE:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position > mDataList.size() -1) return 0;
        return (int) mDataList.get(position).get(Constant.FILM_RECYCLE_ITEM_TYPE);
    }

    @Override
    public int getItemCount() {
//        return mPresenter.getRepositoriesRowsCount();
        return mDataList == null || mDataList.size() == 0 ? 0 : mDataList.size();
    }
}


