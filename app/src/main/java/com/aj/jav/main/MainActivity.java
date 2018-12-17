package com.aj.jav.main;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import com.aj.jav.R;
import com.aj.jav.utils.ViewUtility;

/**
 * footer tab用view而不是直接用fragment是因為不用另外處理add/show問題
 */
public class MainActivity extends AppCompatActivity{
    private Fragment mFragment1, mMainFragment;

    private View mStatusBar;
    private BottomNavigationView mBottomNavigationView;
    private FrameLayout mFooterTab0, mFooterTab1, mFooterTab2, mFooterTab3, mFooterTab4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_tab0, mMainFragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_tab1, mFragment1).commitAllowingStateLoss();
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mBottomNavigationView.setSelectedItemId(savedInstanceState.getInt("bottom_tab"));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("bottom_tab", mBottomNavigationView.getSelectedItemId());
    }

    private void initUI() {
        findViews();
        fixStatusBar();
        setBottomNavigationView();

        mFragment1 = new Fragment();
        mMainFragment = new TabContainerFragment();
    }

    private void findViews() {
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mStatusBar = findViewById(R.id.status_bar_background);

        mFooterTab0 = findViewById(R.id.fl_tab0);
        mFooterTab1 = findViewById(R.id.fl_tab1);
        mFooterTab2 = findViewById(R.id.fl_tab2);
        mFooterTab3 = findViewById(R.id.fl_tab3);
        mFooterTab4 = findViewById(R.id.fl_tab4);
    }

    private void fixStatusBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewUtility.setStatusBarHeightAndColor(this, mStatusBar, getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void setBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(mNavigationItemSelectedListener);
        mBottomNavigationView.setItemIconTintList(null);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            try {
                hideAllView();
                switch (item.getItemId()) {
                    case R.id.navigation_tab0:
                        mFooterTab0.setVisibility(View.VISIBLE);
                        break;
                    case R.id.navigation_tab1:
                        mFooterTab1.setVisibility(View.VISIBLE);
                        break;
                    case R.id.navigation_tab2:
                        mFooterTab2.setVisibility(View.VISIBLE);
                        break;
                    case R.id.navigation_tab3:
                        mFooterTab3.setVisibility(View.VISIBLE);
                        break;
                    case R.id.navigation_tab4:
                        mFooterTab4.setVisibility(View.VISIBLE);
                        break;
                }


                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    };

    private void hideAllView(){
        mFooterTab0.setVisibility(View.GONE);
        mFooterTab1.setVisibility(View.GONE);
        mFooterTab2.setVisibility(View.GONE);
        mFooterTab3.setVisibility(View.GONE);
        mFooterTab4.setVisibility(View.GONE);
    }
}
