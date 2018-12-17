package com.aj.jav.contract;

import android.os.Bundle;

import com.aj.jav.base.BasePresenter;
import com.aj.jav.base.BaseView;
import com.aj.jav.data_model.AdGson;
import com.aj.jav.data_model.MainListGson;

import java.util.List;
import java.util.Map;

public interface MainListContract {
    interface View extends BaseView<Presenter> {
        void showProgress(boolean show);
        void showTopProgress(boolean show);
        void toast(String msg);
        void showOops(boolean show);
        void updateRecycleView();
        void updateRecycleView(int position);
        void insertRecycleViewItem(int positionStart, int itemCount);
        void scrollToPosition(int position);
    }

    interface Presenter extends BasePresenter {
        void insertList(MainListGson gson);
        void init(String menuId, String menuTitle , int itemType , int lastScrollPosition);
        void firstLoadVideoListApi();
        void checkDataAndLoadVideoListApi();
        void reloadVideoListApi();
        int getDataCount();
        void onBindVideoHolderViewAtPosition(VideoHolderView view , int position);
        void onClcikVideoHolder(VideoHolderView view, int position);
        List<Map<String,Object>> getMainList();
        int getItemViewType(int position);
        void likeVideo(String id , boolean like , int position);
        void onBindAdHolderViewAtPosition(AdHolderView view, int position);
        void onClcikAdHolder(AdHolderView view, int position);
        void onBindTagHolderViewAtPosition(TagHolderView view, int position);
        void onClickTag(TagHolderView view, int position , String tag);
    }

    interface VideoHolderView {
        void setTitle(String title);
        void setActor(String actor);
        void setTime(String time);
        void setImage(String url, String referer, int placeHolder);
        void setMainTag(boolean show , String text , int drawableId);
        void setSecTag(boolean isChinese , boolean isNoMark);
        void setLike(String id , boolean like , int position);
        void gotoFilmPage(Bundle bundle);
    }
    
    interface AdHolderView {
        void setAD(List<AdGson.ResponseBean.LongBean> adData);
        void gotoBrowser(String url);
    }

    interface TagHolderView {
        void setTag(List<String> tags , int itemPostion);
        void goToTagPage(Bundle bundle);
    }
}
