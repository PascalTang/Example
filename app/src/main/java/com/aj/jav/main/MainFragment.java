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

/**
 * Created by chris on 2017/10/13.
 * 長片 短片區的分類頁
 */
public class MainFragment extends Fragment implements MainListContract.View {
    private static final String PARAM_POSITION = "ad_position";
    private static final String PARAM_MENU_ID = "menu_id";
    private static final String PARAM_MENU_TITLE = "menu_title";
    private static final String PARAM_SCROLL_POSITION = "scroll";
    private String mMenuId;
    private String mMenuTitle;
    private int mScrollPosition;
    private ImageView mOopsIV;

    private List<Map<String, Object>> mDataList = new ArrayList<>();
    private View mProgress;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMenuId = getArguments().getString(PARAM_MENU_ID);
            mMenuTitle = getArguments().getString(PARAM_MENU_TITLE);
            mScrollPosition = getArguments().getInt(PARAM_SCROLL_POSITION);

            mMainListPresenter.init(getArguments().getInt(PARAM_POSITION),
                    getArguments().getString(PARAM_MENU_ID),
                    getArguments().getString(PARAM_MENU_TITLE),
                    getArguments().getInt(Constant.FILM_RECYCLE_ITEM_TYPE),
                    getArguments().getInt(PARAM_SCROLL_POSITION)
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main_list, container, false);
        mOopsIV = view.findViewById(R.id.iv_oops);
        mProgress = view.findViewById(R.id.v_progress);
        mRecyclerView = view.findViewById(R.id.rv);
        mSwipeRefreshLayout = view.findViewById(R.id.srl_container);
        setRefreshLayout();
        return view;
    }

    private void setRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_blue_bright, R.color.colorPrimaryDark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark, android.R.color.holo_purple);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMainListPresenter.firstTimeLoadVideoListApi();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRecyclerView(getRecyclerViewAdapter(), getSpanCount());

        mMainListPresenter.firstTimeLoadVideoListApi();
    }

    protected void setRecyclerView(final RecyclerView.Adapter adapter, int spanCount) {
        GridLayoutManager layoutManager = new MyGridLayoutManager(getActivity(), spanCount);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = adapter.getItemViewType(position);
                if (type == Constant.FILM_RECYCLE_ITEM_TYPE_NO_MORE || mMainListPresenter.getType() == Constant.DISPLAY_TYPE_LONG_1) {
                    return 2;
                } else return 1;
            }
        });

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    protected RecyclerView.Adapter getRecyclerViewAdapter() {
        return new MainRecyclerAdapter(getActivity(), getDataList(), getType(), mMainListPresenter);
    }

    public int getType() {
        return mMainListPresenter.getType();
    }

    protected List<Map<String, Object>> getDataList() {
        return mMainListPresenter.getMainList();
    }

    protected int getSpanCount() {
        return 2;
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (!recyclerView.canScrollVertically(1) && mProgress.getVisibility() == View.GONE) {
                if (mMainListPresenter.isHaveMoreData()) {
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

    /**
     * Progress show的時候 會擋到原畫面 所以要把它捲到最下面
     */
    private void scrollToBottom() {
        if (mRecyclerView != null && mDataList.size() > 0)
            mRecyclerView.scrollToPosition(mDataList.size() - 1);
    }

//    private void gotoNextPage(Class<?> className) {
//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//
//        bundle.putString("filter_title", mMenuTitle);
//        bundle.putString("menu_id", mMenuId);
//        bundle.putString("video_type", mType);
//        intent.setClass(getActivity(), className);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }

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
