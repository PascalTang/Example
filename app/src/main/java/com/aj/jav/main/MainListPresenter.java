package com.aj.jav.main;

import android.support.annotation.NonNull;
import android.util.Log;
import com.aj.jav.constant.ApiConstant;
import com.aj.jav.constant.Constant;
import com.aj.jav.contract.MainListContract;
import com.aj.jav.data_model.AdGson;
import com.aj.jav.data_model.MainListGson;
import com.aj.jav.observer.MyObserver;
import com.aj.jav.retrofit.ApiClient;
import com.aj.jav.room.dao.MainListEntity;
import com.aj.jav.room.ui.MainListViewModel;
import com.aj.jav.service.ApiService;
import com.aj.jav.utils.ValueUtility;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainListPresenter implements MainListContract.Presenter {
    private CompositeDisposable mCompositeDisposable;
    private final MainListContract.View mView;
    private final MainListViewModel mMainListViewModel;
    private int mCurrentPage = 0;
    private int mTotalPages = -1;
    private static final int ITEM_COUNT_OF_PAGE = 24; //一次顯示多少項目
    private int mAdPosition , mScrollPosition;
    private String mToken, mMenuId, mMenuTitle, mVideoType, mOrder, mTop;
    private List<Map<String, Object>> mDataList = new ArrayList<>();

    public MainListPresenter(@NonNull MainListViewModel mainListViewModel,
                             @NonNull MainListContract.View view) {
        mMainListViewModel = mainListViewModel;
        mView = view;

        mCompositeDisposable = new CompositeDisposable();
        mView.setPresenter(this);
    }


    @Override
    public void insertList(MainListGson gson) {
        mCompositeDisposable.add(mMainListViewModel.insertMainList(gson)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(this::log,
                        // onError
                        throwable -> {
                            Log.i("ddd", "e " + throwable.toString());
                        }));
    }

    @Override
    public void init(int adPosition, String menuId, String menuTitle, int itemType, int lastScrollPosition) {
        this.mToken = "eyJ1c2VyX2lkIjoxNzMwODg3OSwibGFzdGxvZ2luIjoxNTM3MTEyOTY3fQ.8d4bd7f9d373d40a526641b9dac7cc47.a049471dcb12125fb18cfcf7e9599fadd90546f365ce57752faf84bb";
        this.mAdPosition = adPosition;
        this.mMenuId = menuId;
        this.mMenuTitle = menuTitle;
        this.mVideoType = (itemType == Constant.DISPLAY_TYPE_SHORT_2) ? ApiConstant.TYPE_SHORT : ApiConstant.TYPE_LONG;
        this.mScrollPosition = lastScrollPosition;

        if (mMenuTitle.equals("最新")) {
            this.mOrder = "time";
        } else if (mMenuTitle.equals("排行")) {
            this.mOrder = "views";
            this.mTop = "day";
        }
    }

    @Override
    public void firstTimeLoadVideoListApi() {
        mCurrentPage = 0;
        loadVideoListApi();
    }

    /**
     * API 4.1 Get Video List by Menu
     */
    public void loadVideoListApi() {
        mCurrentPage ++;
        //中間可以多放一個viewmodel
        //presenter
        ApiService service = ApiClient.getRetrofit().create(ApiService.class);

        service.getVideoList(mMenuId, mToken, mVideoType, mCurrentPage, mOrder, mTop)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<MainListGson>(MainListGson.class) {
                    @Override
                    public void onComplete(String stringResponse) {
                    }

                    @Override
                    public void onComplete(MainListGson gson) {
                        //fixme 查詢連續呼叫切換線程
                        loadComplete(gson);
                    }

                    @Override
                    public void onError(Throwable e) {
                        switch (e.toString()) {
                            case "tokenChange":
                                Log.i("ddd", "tokenChange ");
                                break;

                            case "tokenError":
                                stopProgress();
//                                new MyBroadcast().sentReLogin(getActivity());
                                break;

                            case "parseGsonError":
                            case "decryptIvError":
                            default:
                                stopProgress();
                                mView.toast("Error");
                                break;
                        }
                    }
                });
    }

    @Override
    public boolean isHaveMoreData() {
        return mCurrentPage < mTotalPages || mTotalPages == -1;
    }

    private void stopProgress(){
        mView.showProgress(false);
        mView.showTopProgress(false);
    }

    private void loadComplete(MainListGson gson){
        insertList(gson);

        stopProgress();

        if (mCurrentPage == 1) {
            int totalResult = gson.getResponse().getTotal_results();
            mTotalPages = ValueUtility.getTotalPages(totalResult, ITEM_COUNT_OF_PAGE);
            mDataList.clear();
            setAD();
            setListData(gson);
            checkOopsShowHide();

            mView.updateRecycleView();
            mView.scrollToPosition(mScrollPosition);

        } else {
            //todo 確認itemCount
            final int itemCount = mDataList.size();
            setListData(gson);
            mView.updateRecycleView(itemCount, mDataList.size());

        }
    }

    private void setAD() {
        String ad = "{\"response\":{\"long\":[{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaac.gif\",\"ad_link_url\":\"http://mimi.503rd.com\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"},{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/340x227_3.gif\",\"ad_link_url\":\"http://mimi.503rd.com\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"},{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaac.gif\",\"ad_link_url\":\"http://mimi.503rd.com\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"},{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/340x227_3.gif\",\"ad_link_url\":\"http://mimi.503rd.com\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"},{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaac.gif\",\"ad_link_url\":\"http://mimi.503rd.com\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"}],\"short\":[{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaad.gif\",\"ad_link_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaad.gif\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"},{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaad.gif\",\"ad_link_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaad.gif\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"},{\"ad_img_url\":\"https://s1.imgs.cc/img/aaaaa43YQ.jpg\",\"ad_link_url\":\"https://sops.kilosaglik.com/vivi/gametree/1116TiehSiehJhihJhan.apk\",\"ad_title\":\"铁血之战，战无不胜\"},{\"ad_img_url\":\"https://s1.imgs.cc/img/aaaaa43YQ.jpg\",\"ad_link_url\":\"https://sops.kilosaglik.com/vivi/gametree/1116TiehSiehJhihJhan.apk\",\"ad_title\":\"铁血之战，战无不胜\"},{\"ad_img_url\":\"https://s1.imgs.cc/img/aaaaa4P0F.gif\",\"ad_link_url\":\"http://t.cn/E2R2XPA\",\"ad_title\":\"剧迷：平常看不到的、这里通通有\"}]},\"status\":{\"code\":200,\"message\":\"success\"}}";

        AdGson adGson = new Gson().fromJson(ad, AdGson.class);
        //AdGson adGson = new Gson().fromJson(SharedPreferenceHelper.getAD(), AdGson.class);

        Map<String, Object> map = new HashMap<>();

        map.put(Constant.FILM_RECYCLE_ITEM_TYPE, Constant.FILM_RECYCLE_ITEM_TYPE_AD);
        if (mVideoType.equals("long")) {
            map.put("img", adGson.getResponse().getLongX().get(mAdPosition).getAd_img_url());
            map.put("title", adGson.getResponse().getLongX().get(mAdPosition).getAd_title());
            map.put("link", adGson.getResponse().getLongX().get(mAdPosition).getAd_link_url());
        } else {
            map.put("img", adGson.getResponse().getShortX().get(mAdPosition).getAd_img_url());
            map.put("title", adGson.getResponse().getShortX().get(mAdPosition).getAd_title());
            map.put("link", adGson.getResponse().getShortX().get(mAdPosition).getAd_link_url());
        }

        mDataList.add(map);
    }

    private void setListData(MainListGson gson) {

        if (null != gson) {

            for (int i = 0; i < gson.getResponse().getVideos().size(); i++) {

                Map<String, Object> map = new HashMap<>();

                map.put(Constant.FILM_RECYCLE_ITEM_TYPE, Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_LIST);
                map.put("id", gson.getResponse().getVideos().get(i).getVideo_id());
                map.put("title", gson.getResponse().getVideos().get(i).getVideo_title());
                map.put("actor", gson.getResponse().getVideos().get(i).getActor());
                map.put("like", gson.getResponse().getVideos().get(i).isVideo_like());
                map.put("cover_url", gson.getResponse().getVideos().get(i).getCover());
                map.put("duration", gson.getResponse().getVideos().get(i).getVideo_duration());

                if (gson.getResponse().getVideos().get(i).getUpload_date() == 0) {
                    map.put("date", gson.getResponse().getVideos().get(i).getRelease_date());
                } else {
                    map.put("date", gson.getResponse().getVideos().get(i).getUpload_date());
                }

                if (gson.getResponse().getVideos().get(i).getMain_tag().size() > 0) {
                    map.put("main_tag", gson.getResponse().getVideos().get(i).getMain_tag().get(0));
                } else {
                    map.put("main_tag", "");
                }

                if (gson.getResponse().getVideos().get(i).getSecond_tag().size() > 0) {
                    List<String> secondTag = new ArrayList<>();

                    secondTag.addAll(gson.getResponse().getVideos().get(i).getSecond_tag());

                    map.put("second_tag", secondTag);
                }

                mDataList.add(map);
            }
        }
    }

    private void checkOopsShowHide() {
        mView.showOops(mDataList.size() <= 0);
    }



    @Override
    public int getRepositoriesRowsCount() {
//        return mMainListViewModel.getSize();
        return mDataList.size();
    }

    @Override
    public void onBindRepositoryRowViewAtPosition(MainListContract.RepositoryRowView view, int position) {
        Repository repo = mDataList.get(position).get();
        view.setStarCount(repo.getStarsCount());
        view.setTitle(repo.getTitle());
    }

    @Override
    public List<Map<String, Object>> getMainList() {
        return mDataList;
    }

    @Override
    public int getType() {
        return mVideoType.equals(ApiConstant.TYPE_LONG) ? Constant.DISPLAY_TYPE_LONG_1 : Constant.DISPLAY_TYPE_SHORT_2;
    }

    @Override
    public void subscribe() {
        mCompositeDisposable.add(mMainListViewModel.getMainList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<List<MainListEntity>>() {
                    @Override
                    public void accept(List<MainListEntity> mainListEntities) throws Exception {
                        Log.i("ddd", "getMainList " + mainListEntities.size());
                    }
                }));
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    private void log() {
        Log.i("ddd", "ok " + mMainListViewModel.getSize());
    }
}
