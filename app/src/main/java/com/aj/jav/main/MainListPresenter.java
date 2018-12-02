package com.aj.jav.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.aj.jav.R;
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
import com.aj.jav.service.BaseDomain;
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
    private int mAdPosition, mScrollPosition, mItemType;
    private String mToken, mMenuId, mMenuTitle, mVideoType, mOrder, mTop;
    private List<Map<String, Object>> mDataList = new ArrayList<>();
    private boolean mIsFirstTimeLoadData = true;

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
        this.mItemType = itemType;
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
    public void firstLoadVideoListApi() {
        if (mIsFirstTimeLoadData){
            mIsFirstTimeLoadData = false;
            loadVideoListApi();
        }
    }

    @Override
    public void reloadVideoListApi() {
        mCurrentPage = 0;
        mScrollPosition = 0;
        Log.i("ddd","reloadVideoListApi "+mScrollPosition);
        loadVideoListApi();
    }

    /**
     * API 4.1 Get Video List by Menu
     */
    public void loadVideoListApi() {
        mCurrentPage++;
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

    private void stopProgress() {
        mView.showProgress(false);
        mView.showTopProgress(false);
    }

    private void loadComplete(MainListGson gson) {
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
    public int getDataCount() {
//        return mMainListViewModel.getSize();
        return mDataList.size();
    }

    @Override
    public void onBindVideoHolderViewAtPosition(MainListContract.VideoHolderView view, int position) {
        view.setTitle((String) mDataList.get(position).get("title"));
        view.setActor((String) mDataList.get(position).get("actor"));
        view.setImage(BaseDomain.sBaseImageDomain + mDataList.get(position).get("cover_url"), BaseDomain.sBaseImageReferer, getImagePlaceHolderId());
        view.setTime(getTime(position));
        view.setItemClick((String) mDataList.get(position).get("id"));

        String mainTag = getMainTag(position);
        view.setMainTag(!mainTag.isEmpty(), transparentString(mainTag) , getMainTagBG(mainTag));


        view.setSecTag(isTagChinese(position) , isTagNoMark(position));
        view.setLike((String) mDataList.get(position).get("id"), (boolean) mDataList.get(position).get("like"), position);
    }

    private int getImagePlaceHolderId() {
        switch (mItemType) {
            case Constant.DISPLAY_TYPE_LONG_2:
                return R.drawable.ic_image_default_straight;
            case Constant.DISPLAY_TYPE_LONG_1:
            case Constant.DISPLAY_TYPE_SHORT_2:
                return R.drawable.ic_image_default_horizontal;
            default:
                return 0;
        }
    }

    private String getTime(int position) {
        switch (mItemType) {
            case Constant.DISPLAY_TYPE_LONG_1:
            case Constant.DISPLAY_TYPE_LONG_2:
                return (ValueUtility.getDate((long) mDataList.get(position).get("date")));
            case Constant.DISPLAY_TYPE_SHORT_2:
                return (ValueUtility.getTime(String.valueOf((int) mDataList.get(position).get("duration"))));
            default:
                return "";
        }
    }

    private String getMainTag(int position){
        return  (String) mDataList.get(position).get("main_tag");
    }

    private int getMainTagBG(String tag){
        switch (tag){
            case "限免":
                return R.drawable.bg_gradient_main_tag_orange;
            case "獨家":
                return R.drawable.bg_gradient_main_tag_red;
            default:
                return 0;
        }
    }

    private String transparentString(String s) {
        if (!ValueUtility.isCN()) return s;
        switch (s) {
            case "獨家":
                return "独家";
            default:
                return s;
        }
    }

    private boolean isTagChinese(int position){
        return isTagShow(position , "中");
    }

    private boolean isTagNoMark(int position){
        return isTagShow(position , "無");
    }

    private boolean isTagShow(int position , String tag){
        switch (getSecTagSize(position)){
            case 1:
                return getSecTag(position).equals(tag);
            case 2:
                return true;
            default:
                return false;
        }
    }

    private int getSecTagSize(int position){
        List<String> secondTags = getSecTags(position);
        if (secondTags == null)return 0;
        return secondTags.size();
    }

    private String getSecTag(int position){
        List<String> secondTags = getSecTags(position);
        return secondTags.get(0);
    }

    private List<String> getSecTags(int position){
        return (List<String>) mDataList.get(position).get("second_tag");
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
    public void likeVideo(String id , boolean like , int position) {

    }

    /**
     * @param id 影片id
     */
    public void setGA(String id) {
        switch (mItemType) {
            case Constant.DISPLAY_TYPE_LONG_1:
            case Constant.DISPLAY_TYPE_LONG_2:
//                GaHelper.getInstance().setTrackEvents("LongFilmListPage.action", "GoToLongFilmPlayPage", id);
                break;
            case Constant.DISPLAY_TYPE_SHORT_2:
//                GaHelper.getInstance().setTrackEvents("ShortFilmListPage.action", "GoToShortFilmPlayPage", id);
                break;
        }
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
