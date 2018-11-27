package com.aj.jav.helper;

import android.content.Context;
import android.widget.ImageView;

import com.aj.jav.constant.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import de.hdodenhof.circleimageview.CircleImageView;

public class GlideHelper {
    public static GlideUrl getUrlWithHeader(String url, String referer) {
        return new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader(Constant.IMAGE_HEADER_KEY, referer)
                .build());
    }

    public static void setImage(Context context, String url, String referer, int placeHolder, ImageView view) {
        if (url == null || !url.isEmpty()) {
            Glide.with(context)
                    .load(referer != null ? GlideHelper.getUrlWithHeader(url, referer) : url)
                    .apply(RequestOptions.placeholderOf(placeHolder))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .into(view);
        } else {
            Glide.with(context).load(placeHolder).into(view);
        }
    }

    public static void setCircleImage(final Context context, String url, String referer, int placeHolder, CircleImageView view) {
        if (url == null || !url.isEmpty()) {
            Glide.with(context)
                    .asBitmap()
                    .load(referer != null ? GlideHelper.getUrlWithHeader(url, referer) : url)
                    .apply(RequestOptions.placeholderOf(placeHolder))
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .into(view);
        } else {
            Glide.with(context).load(placeHolder).into(view);
        }
    }
}
