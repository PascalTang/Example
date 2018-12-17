package com.aj.jav.recycle_holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aj.jav.R;
import com.aj.jav.base.MyApplication;
import com.aj.jav.contract.MainListContract;
import com.aj.jav.film.FilmActivity;

import java.util.List;

public class TagHolder extends RecyclerView.ViewHolder implements MainListContract.TagHolderView {

    private MainListContract.Presenter mPresenter;
    private Context mContext;
    private View mRootView;
    public TagHolder(Context context, MainListContract.Presenter presenter, View itemView) {
        super(itemView);
        this.mContext = context;
        this.mPresenter = presenter;
        this.mRootView = itemView;
    }

    @Override
    public void setTag(List<String> tags , int itemPosition) {
        for (int tagPosition = 0 ; tagPosition < tags.size() ; tagPosition ++ ){
            TextView tv = mRootView.findViewById(mContext.getResources().getIdentifier("tv_tag"+tagPosition, "id", MyApplication.getAppContext().getPackageName()));
            String tag = tags.get(tagPosition);
            tv.setText(tag);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.onClickTag(TagHolder.this , itemPosition , tag);
                }
            });
        }
    }

    @Override
    public void goToTagPage(Bundle bundle) {
        Intent intent = new Intent(mContext, FilmActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}