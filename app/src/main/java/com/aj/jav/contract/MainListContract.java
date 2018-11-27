package com.aj.jav.contract;

import com.aj.jav.base.BasePresenter;
import com.aj.jav.base.BaseView;
import com.aj.jav.data_model.MainListGson;

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

        void loadVideoListApi();

        int getRepositoriesRowsCount();

        void onBindRepositoryRowViewAtPosition(RepositoryRowView holder , int position);

    }

    interface RepositoryRowView {

        void setTitle(String title);

        void setStarCount(int starCount);
    }
}
