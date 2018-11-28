package com.aj.jav.contract;

import android.widget.ImageView;

import com.aj.jav.base.BasePresenter;
import com.aj.jav.base.BaseView;
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
        void updateRecycleView(int positionStart, int itemCount);
        void scrollToPosition(int position);
    }

    interface Presenter extends BasePresenter {
        void insertList(MainListGson gson);

//        void loadVideoList();

        void init(int adPosition , String menuId, String menuTitle , int itemType , int lastScrollPosition);

        void firstTimeLoadVideoListApi();

        void loadVideoListApi();

        boolean isHaveMoreData();

        int getDataCount();

        void onBindVideoHolderViewAtPosition(VideoHolderView holder , int position);

        List<Map<String,Object>> getMainList();

        int getType();

        void likeVideo(String id , boolean like , int position);

        void clcikItem();
    }

    interface VideoHolderView {
        void setTitle(String title);
        void setActor(String actor);
        void setTime(String time);
        void setImage(String url, String referer, int placeHolder);
        void setMainTag(boolean show , String text , int drawableId);
        void setSecTag(boolean isChinese , boolean isNoMark);
        void setLike(String id , boolean like , int position);
    }
}
