package com.aj.jav.film;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aj.jav.R;
import com.aj.jav.room.Injection;
import com.aj.jav.room.ui.MainListViewModel;
import com.aj.jav.room.ui.ViewModelFactory;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FilmActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewModelFactory mViewModelFactory;
    private Button mLikeBtn, mUnlikeBtn;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MainListViewModel mModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        String videoId = getIntent().getStringExtra("video_id");
        String videoType = getIntent().getStringExtra("video_type");

        Log.i("ddd","videoId "+videoId);
        Log.i("ddd","videoType "+videoType);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mModel = ViewModelProviders.of(this, mViewModelFactory).get(MainListViewModel.class);

        mLikeBtn = findViewById(R.id.btn_like);
        mLikeBtn.setOnClickListener(this);
        mUnlikeBtn = findViewById(R.id.btn_unlike);
        mUnlikeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_like:
//                mCompositeDisposable.add(mModel.updateLike()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(Schedulers.io())
//                        .subscribe());
                break;
            case R.id.btn_unlike:

                break;
        }
    }
}
