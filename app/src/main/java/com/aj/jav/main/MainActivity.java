package com.aj.jav.main;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aj.jav.R;
import com.aj.jav.constant.Constant;
import com.aj.jav.room.Injection;
import com.aj.jav.room.ui.MainListViewModel;
import com.aj.jav.room.ui.ViewModelFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewModelFactory mViewModelFactory;

    private MainFragment fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        MainListViewModel ViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainListViewModel.class);

        fragment1 = MainFragment.newInstance(20001, 0, "0", "最新" ,position);
//        fragment2 = MainFragment.newInstance(20001, 1, "0", "排行" , position);

        MainListPresenter mMainListPresenter = new MainListPresenter(ViewModel ,fragment1);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);

//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction().add(R.id.container, Fragment1.newInstance(position)).commit();
//        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn1:
            case R.id.btn2:

                Bundle args = new Bundle();
                args.putInt("ad_position", 0);
                args.putString("menu_id", "0");
                args.putString("menu_title", "最新");
                args.putInt(Constant.FILM_RECYCLE_ITEM_TYPE, Constant.DISPLAY_TYPE_LONG_2);
                args.putInt("scroll", position);

                fragment1.setArguments(args);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment1, "f2")
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
