package com.aj.jav.recycle_holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aj.jav.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;
import java.util.Map;

public class AdHolder extends RecyclerView.ViewHolder {

    private Context mContext;

    private TextView textTitle;
    private ImageView mGifImageView;

    public AdHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;

        textTitle = itemView.findViewById(R.id.text_title);
        mGifImageView = itemView.findViewById(R.id.gif_iv);
    }

    public void onBindViewHolder(AdHolder videoHolder, final int position, final List<Map<String, Object>> dataList) {
        videoHolder.textTitle.setText((String) dataList.get(position).get("title"));

        Glide.with(mContext).load(dataList.get(0).get("img")).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)).into(mGifImageView);

        videoHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = (String) dataList.get(position).get("link");
                if (link.toLowerCase().equals("vip")){
                    //todo to somepage
//                    mContext.startActivity(new Intent(mContext, BuyMemberActivity.class));
                }else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    mContext.startActivity(browserIntent);
                }

            }
        });
    }
}