package com.aj.jav.recycle_holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aj.jav.R;
import com.aj.jav.contract.MainListContract;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class AdHolder extends RecyclerView.ViewHolder implements MainListContract.AdHolderView, View.OnClickListener {

    private MainListContract.Presenter mPresenter;
    private Context mContext;
    private TextView mTextTitleTV;
    private ImageView mGifImageView;

    public AdHolder(Context context, MainListContract.Presenter presenter, View itemView) {
        super(itemView);
        this.mContext = context;
        this.mPresenter = presenter;
        this.mTextTitleTV = itemView.findViewById(R.id.title_tv);
        this.mGifImageView = itemView.findViewById(R.id.gif_iv);
        this.itemView.setOnClickListener(this);
    }

    @Override
    public void setTitle(String title) {
        mTextTitleTV.setText(title);

    }

    @Override
    public void setImage(String url) {
        Glide.with(mContext).load(url).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)).into(mGifImageView);

    }

    @Override
    public void onClick(View view) {
        mPresenter.onAdHolderOnclcik(this, getAdapterPosition());
    }

    @Override
    public void gotoBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        mContext.startActivity(browserIntent);
    }
}