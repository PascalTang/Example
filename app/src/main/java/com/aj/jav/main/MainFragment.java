package com.aj.jav.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aj.jav.R;
import com.aj.jav.constant.ApiConstant;
import com.aj.jav.constant.Constant;
import com.aj.jav.contract.MainListContract;
import com.aj.jav.data_model.AdGson;
import com.aj.jav.data_model.MainListGson;
import com.aj.jav.layoutmanager.MyGridLayoutManager;
import com.aj.jav.observer.MyObserver;
import com.aj.jav.recycle_adapter.MainRecyclerAdapter;
import com.aj.jav.retrofit.ApiClient;
import com.aj.jav.service.ApiService;
import com.aj.jav.utils.ValueUtility;
import com.aj.jav.utils.ViewUtility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chris on 2017/10/13.
 * 長片 短片區的分類頁
 */
public class MainFragment extends Fragment implements MainListContract.View {
    private static final String PARAM_POSITION = "ad_position";
    private static final String PARAM_MENU_ID = "menu_id";
    private static final String PARAM_MENU_TITLE = "menu_title";
    private static final String PARAM_SCROLL_POSITION = "scroll";
    private int mAdPosition;
    private String mMenuId;
    private String mMenuTitle;
    private int mScrollPosition;
    private ImageView mOopsIV;

    private List<Map<String, Object>> mDataList = new ArrayList<>();
    private int mCurrentPage = 1;
    private int mTotalPages = -1;
    private static final int ITEM_NUMBER_ONE_PAGE = 24; //一次顯示多少項目
    private View mProgress;
    private String mType;
    private MainListContract.Presenter mMainListPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mRecyclerView;

    public static MainFragment newInstance(int type, int adPosition, String menuId, String menuTitle, int srcollPosition) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(PARAM_POSITION, adPosition);
        args.putString(PARAM_MENU_ID, menuId);
        args.putString(PARAM_MENU_TITLE, menuTitle);
        args.putInt(Constant.FILM_RECYCLE_ITEM_TYPE, type);
        args.putInt(PARAM_SCROLL_POSITION, srcollPosition);
        fragment.setArguments(args);

        return fragment;
    }

    protected void findViews(View view, Bundle savedInstanceState) {
        mOopsIV = view.findViewById(R.id.iv_oops);
        mProgress = view.findViewById(R.id.v_progress);
        mRecyclerView = view.findViewById(R.id.rv);
        mSwipeRefreshLayout = view.findViewById(R.id.srl_container);
        setRefreshLayout();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAdPosition = getArguments().getInt(PARAM_POSITION);
            mMenuId = getArguments().getString(PARAM_MENU_ID);
            mMenuTitle = getArguments().getString(PARAM_MENU_TITLE);
            mScrollPosition = getArguments().getInt(PARAM_SCROLL_POSITION);

            int type = getArguments().getInt(Constant.FILM_RECYCLE_ITEM_TYPE);
            if (type == Constant.DISPLAY_TYPE_SHORT_2) mType = "short";
            else if (type == Constant.DISPLAY_TYPE_LONG_2) mType = "long";

            mMainListPresenter.init(getArguments().getInt(PARAM_POSITION),
                    getArguments().getString(PARAM_MENU_ID),
                    getArguments().getString(PARAM_MENU_TITLE),
                    getArguments().getInt(Constant.FILM_RECYCLE_ITEM_TYPE),
                    mScrollPosition = getArguments().getInt(PARAM_SCROLL_POSITION)
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main_list, container, false);
        findViews(view, savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRecyclerView(getDataList(), getRecyclerViewAdapter(), getSpanCount());

        if (!TextUtils.isEmpty(mMenuId)) {
//            loadVideoListApi(mMenuId, mType, mCurrentPage);

            mMainListPresenter.loadVideoListApi();

        }
    }

    protected List<Map<String, Object>> getDataList() {
        return mDataList;
    }

    protected RecyclerView.Adapter getRecyclerViewAdapter() {
        int type = mType.equals(ApiConstant.TYPE_LONG) ? Constant.DISPLAY_TYPE_LONG_1 : Constant.DISPLAY_TYPE_SHORT_2;

        MainRecyclerAdapter adapter = new MainRecyclerAdapter(getActivity(), getDataList(), type, mMainListPresenter);
//        adapter.setFilmPresenter(mPresenter);
        return adapter;
    }

    protected int getSpanCount() {
        return 2;
    }

    private void setRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_blue_bright, R.color.colorPrimaryDark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark, android.R.color.holo_purple);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 1;
                mMainListPresenter.loadVideoListApi();
            }
        });
    }

    protected void setRecyclerView(List<Map<String, Object>> dataList, final RecyclerView.Adapter adapter, int spanCount) {
        GridLayoutManager layoutManager = new MyGridLayoutManager(getActivity(), spanCount);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = adapter.getItemViewType(position);
                if (type == Constant.FILM_RECYCLE_ITEM_TYPE_NO_MORE
                        || mType.equals(ApiConstant.TYPE_LONG)) {
                    return 2;
                } else return 1;
            }
        });

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (!recyclerView.canScrollVertically(1) && mProgress.getVisibility() == View.GONE) {
                if (isHaveMoreData()) {
                    mCurrentPage++;
                    mMainListPresenter.loadVideoListApi();
                    showProgress(true);
                    scrollToBottom();
                } else {
                    showNoMore();
                }
            }
        }
    };

    public void showNoMore() {
        if (mDataList == null || mDataList.size() == 0 || (int) mDataList.get(mDataList.size() - 1).get(Constant.FILM_RECYCLE_ITEM_TYPE) == Constant.FILM_RECYCLE_ITEM_TYPE_NO_MORE)
            return;

        Map<String, Object> map = new HashMap<>();
        map.put(Constant.FILM_RECYCLE_ITEM_TYPE, Constant.FILM_RECYCLE_ITEM_TYPE_NO_MORE);
        mDataList.add(map);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.getAdapter().notifyItemChanged(mDataList.size() - 1);
            }
        });
    }

    public boolean isHaveMoreData() {
        return mCurrentPage < mTotalPages || mTotalPages == -1;
    }

    /**
     * Progress show的時候 會擋到原畫面 所以要把它捲到最下面
     */
    private void scrollToBottom() {
        if (mRecyclerView != null && mDataList.size() > 0)
            mRecyclerView.scrollToPosition(mDataList.size() - 1);
    }

    private void gotoNextPage(Class<?> className) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        bundle.putString("filter_title", mMenuTitle);
        bundle.putString("menu_id", mMenuId);
        bundle.putString("video_type", mType);
        intent.setClass(getActivity(), className);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void loadComplete(MainListGson gson) {
        if (getActivity() == null) return;

        mMainListPresenter.insertList(gson);

        showProgress(false);
        showTopProgress(false);
        if (mCurrentPage == 1) {
            int totalResult = gson.getResponse().getTotal_results();
            mTotalPages = ValueUtility.getTotalPages(totalResult, ITEM_NUMBER_ONE_PAGE);
            mDataList.clear();
            setAD();
            setListData(gson);
            checkOopsShowHide();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                    mRecyclerView.scrollToPosition(mScrollPosition);
                }
            });
        } else {
            final int itemCount = mRecyclerView.getAdapter().getItemCount();
            setListData(gson);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mRecyclerView.getAdapter().notifyItemRangeInserted(itemCount, mDataList.size());
                }
            });
        }
    }

    private void setAD() {
        String ad = "{\"response\":{\"long\":[{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaac.gif\",\"ad_link_url\":\"http://mimi.503rd.com\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"},{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/340x227_3.gif\",\"ad_link_url\":\"http://mimi.503rd.com\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"},{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaac.gif\",\"ad_link_url\":\"http://mimi.503rd.com\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"},{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/340x227_3.gif\",\"ad_link_url\":\"http://mimi.503rd.com\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"},{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaac.gif\",\"ad_link_url\":\"http://mimi.503rd.com\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"}],\"short\":[{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaad.gif\",\"ad_link_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaad.gif\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"},{\"ad_img_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaad.gif\",\"ad_link_url\":\"http://cdn.adultsmate.com/guanwang/lutube/aaaaad.gif\",\"ad_title\":\"丰富棋牌，超高返水！还不快来玩～\"},{\"ad_img_url\":\"https://s1.imgs.cc/img/aaaaa43YQ.jpg\",\"ad_link_url\":\"https://sops.kilosaglik.com/vivi/gametree/1116TiehSiehJhihJhan.apk\",\"ad_title\":\"铁血之战，战无不胜\"},{\"ad_img_url\":\"https://s1.imgs.cc/img/aaaaa43YQ.jpg\",\"ad_link_url\":\"https://sops.kilosaglik.com/vivi/gametree/1116TiehSiehJhihJhan.apk\",\"ad_title\":\"铁血之战，战无不胜\"},{\"ad_img_url\":\"https://s1.imgs.cc/img/aaaaa4P0F.gif\",\"ad_link_url\":\"http://t.cn/E2R2XPA\",\"ad_title\":\"剧迷：平常看不到的、这里通通有\"}]},\"status\":{\"code\":200,\"message\":\"success\"}}";

        AdGson adGson = new Gson().fromJson(ad, AdGson.class);
        //AdGson adGson = new Gson().fromJson(SharedPreferenceHelper.getAD(), AdGson.class);

        Map<String, Object> map = new HashMap<>();

        map.put(Constant.FILM_RECYCLE_ITEM_TYPE, Constant.FILM_RECYCLE_ITEM_TYPE_AD);
        if (mType.equals("long")) {
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

    /**
     * 顯示/隱藏OOPS圖片
     */
    private void checkOopsShowHide() {
        mOopsIV.setVisibility(mDataList.size() > 0 ? View.INVISIBLE : View.VISIBLE);
    }


    @Override
    public void showProgress(boolean b) {
        if (getActivity() == null || getActivity().isDestroyed() || getActivity().isFinishing() || mProgress == null)
            return;
        mProgress.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showTopProgress(boolean b) {
        if (getActivity() == null || getActivity().isDestroyed() || getActivity().isFinishing() || mSwipeRefreshLayout == null)
            return;
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void toast(String msg) {
        ViewUtility.showToast(getActivity(), msg);
    }

    @Override
    public void showOops(boolean show) {
        mOopsIV.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void updateRecycleView() {
        if (mRecyclerView.getAdapter() != null)
            mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void updateRecycleView(int positionStart, int itemCount) {
        if (mRecyclerView.getAdapter() != null)
            mRecyclerView.getAdapter().notifyItemRangeInserted(itemCount, mDataList.size());
    }

    @Override
    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(mScrollPosition);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LinearLayoutManager myLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int scrollPosition = myLayoutManager.findFirstVisibleItemPosition();

        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;
        activity.setPosition(scrollPosition);

    }

    @Override
    public void setPresenter(MainListContract.Presenter presenter) {
        mMainListPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainListPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMainListPresenter.unsubscribe();
    }


}
