package com.aj.jav.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aj.jav.R;
import com.aj.jav.constant.Constant;
import com.aj.jav.contract.MainListContract;
import com.aj.jav.layoutmanager.MyGridLayoutManager;
import com.aj.jav.recycle_adapter.VideoListAdapter;
import com.aj.jav.room.Injection;
import com.aj.jav.room.ui.MainListViewModel;
import com.aj.jav.room.ui.ViewModelFactory;
import com.aj.jav.utils.ViewUtility;

/**
 * Created by chris on 2017/10/13.
 * 長片 短片區的分類頁
 */
public class VideoListFragment extends Fragment implements MainListContract.View {
    private static final String PARAM_MENU_ID = "menu_id";
    private static final String PARAM_MENU_TITLE = "menu_title";
    private static final String PARAM_SCROLL_POSITION = "scroll";
    private ImageView mOopsIV;

    private View mProgress;
    private MainListContract.Presenter mMainListPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mRecyclerView;

    public static VideoListFragment newInstance(int type, String menuId, String menuTitle, int srcollPosition) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle args = new Bundle();
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
            ViewModelFactory pViewModelFactory = Injection.provideViewModelFactory(this.getActivity());
            mMainListPresenter = new MainListPresenter(ViewModelProviders.of(this, pViewModelFactory).get(MainListViewModel.class), this);

            mMainListPresenter.init(getArguments().getString(PARAM_MENU_ID),
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
                mMainListPresenter.reloadVideoListApi();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRecyclerView(getRecyclerViewAdapter(), getSpanCount());
        mMainListPresenter.firstLoadVideoListApi();
    }

    protected void setRecyclerView(final RecyclerView.Adapter adapter, int spanCount) {
        GridLayoutManager layoutManager = new MyGridLayoutManager(getActivity(), spanCount);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = adapter.getItemViewType(position);
                if (type == Constant.FILM_RECYCLE_ITEM_TYPE_NO_MORE
                        || type == Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_LONG_1
                        || type == Constant.FILM_RECYCLE_ITEM_TYPE_AD_LONG
                        || type == Constant.FILM_RECYCLE_ITEM_TYPE_TAG
                        ) {
                    return 2;
                } else return 1;
            }
        });

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    protected RecyclerView.Adapter getRecyclerViewAdapter() {
        return new VideoListAdapter(getActivity(), mMainListPresenter);
    }

    protected int getSpanCount() {
        return 2;
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (!recyclerView.canScrollVertically(1) && mProgress.getVisibility() == View.GONE) {
                mMainListPresenter.checkDataAndLoadVideoListApi();
            }
        }
    };

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
    public void updateRecycleView(int position) {
        if (mRecyclerView.getAdapter() != null)
            mRecyclerView.getAdapter().notifyItemChanged(position);
    }

    @Override
    public void insertRecycleViewItem(int positionStart, int itemCount) {
        if (mRecyclerView.getAdapter() != null)
            mRecyclerView.getAdapter().notifyItemRangeInserted(itemCount, mMainListPresenter.getMainList().size());
    }

    @Override
    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LinearLayoutManager myLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        //todo 如果是null會?
        assert myLayoutManager != null;
        int scrollPosition = myLayoutManager.findFirstVisibleItemPosition();

        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;
        //todo 儲存scrollPosition

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
