package com.aj.jav.recycle_holder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.aj.jav.R;
import com.aj.jav.contract.MainListContract;
import com.aj.jav.film.FilmActivity;
import com.aj.jav.helper.GlideHelper;

/**
 * Created by pascal on 2018/3/27.
 * 長片1欄/2欄/短片2欄 的VideoHolder
 */

public class VideoHolder extends RecyclerView.ViewHolder implements MainListContract.VideoHolderView, View.OnClickListener {

    private MainListContract.Presenter mPresenter;
    private Context mContext;

    private TextView textDate;
    private TextView textTitle;
    private TextView textActor;
    private TextView textMainTag;
    private TextView textSecondTag1;
    private TextView textSecondTag2;
    private ImageView imageCover;
    private ToggleButton toggleLike;

    public VideoHolder(Context context, MainListContract.Presenter presenter, View itemView) {
        super(itemView);
        this.mContext = context;
        this.mPresenter = presenter;
        this.textDate = itemView.findViewById(R.id.text_date);
        this.textTitle = itemView.findViewById(R.id.text_title);
        this.textActor = itemView.findViewById(R.id.text_actor);
        this.imageCover = itemView.findViewById(R.id.image_cover);
        this.textMainTag = itemView.findViewById(R.id.text_main_tag);
        this.textSecondTag1 = itemView.findViewById(R.id.text_second_tag_1);
        this.textSecondTag2 = itemView.findViewById(R.id.text_second_tag_2);
        this.toggleLike = itemView.findViewById(R.id.toggle_like);
        this.itemView.setOnClickListener(this);
    }

    @Override
    public void setTitle(String title) {
        textTitle.setText(title);
    }

    @Override
    public void setActor(String actor) {
        textActor.setText(actor);
    }

    @Override
    public void setTime(String time) {
        textDate.setText(time);
    }

    @Override
    public void setImage(String url, String referer, int placeHolder) {
        GlideHelper.setImage(mContext, url, referer, placeHolder, imageCover);
    }

    @Override
    public void setMainTag(boolean show, String text, int drawableId) {
        textMainTag.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        textMainTag.setText(text);
        if (drawableId != 0)
            textMainTag.setBackground(mContext.getResources().getDrawable(drawableId));
    }

    @Override
    public void setSecTag(boolean isChinese, boolean isNoMark) {
        textSecondTag1.setVisibility(isChinese ? View.VISIBLE : View.INVISIBLE);
        textSecondTag2.setVisibility(isNoMark ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setLike(String id, boolean like, int position) {
        toggleLike.setChecked(like);
        toggleLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.likeVideo(id, !like, position);
            }
        });
    }

    @Override
    public void onClick(View view) {
        mPresenter.onVideoHolderOnclcik(this , getAdapterPosition());
    }

    @Override
    public void gotoFilmPage(Bundle bundle) {
        Intent intent = new Intent(mContext, FilmActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}