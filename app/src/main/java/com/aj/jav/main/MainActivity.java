package com.aj.jav.main;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.aj.jav.R;
import com.aj.jav.constant.Constant;
import com.aj.jav.contract.MainListContract;
import com.aj.jav.room.Injection;
import com.aj.jav.room.ui.MainListViewModel;
import com.aj.jav.room.ui.ViewModelFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewModelFactory mViewModelFactory;

    private VideoListFragment mFragment1;
    private Fragment mFragment2;

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        MainListViewModel ViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainListViewModel.class);

        mFragment1 = VideoListFragment.newInstance(Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_LONG_1, 0, "0", "最新" ,position);
        mFragment2 = new VideoListTabContainerFragment();

        MainListContract.Presenter mMainListPresenter = new MainListPresenter(ViewModel , mFragment1);

        initUI();

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);

//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction().add(R.id.container, Fragment1.newInstance(position)).commit();
//        }

    }

    private void initUI() {
        findViews();
        initStatusBar();
        setBottomNavigationView();
    }

    private void findViews() {
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void initStatusBar() {
    }

    private void setBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(mNavigationItemSelectedListener);
//        mBottomNavigationView.disableShiftMode(bottomNavigationView);
        mBottomNavigationView.setItemIconTintList(null);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            try {
//                hideLastFragment();
//
//                sFragmentId = item.getItemId();
//
//                String tag = getFragmentTag();
//                intoPage(getFragment(), tag);
//                setGaClickEvents(tag);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    };

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn1:

                Bundle args = new Bundle();
                args.putInt("ad_position", 0);
                args.putString("menu_id", "0");
                args.putString("menu_title", "最新");
                args.putInt(Constant.FILM_RECYCLE_ITEM_TYPE, Constant.FILM_RECYCLE_ITEM_TYPE_VIDEO_LONG_1);
                args.putInt("scroll", position);

                mFragment1.setArguments(args);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, mFragment1, "f1")
                        .commit();
                break;

            case R.id.btn2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, mFragment2, "f2")
                        //.addToBackStack("fname")
                        .commit();
                break;
        }
    }

    private int position = 0 ;
    public void setPosition(int i){
        position = i;
    }
}
