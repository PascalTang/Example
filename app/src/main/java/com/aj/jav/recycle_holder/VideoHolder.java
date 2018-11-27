package com.aj.jav.recycle_holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.aj.jav.R;
import com.aj.jav.constant.Constant;
import com.aj.jav.contract.MainListContract;
import com.aj.jav.helper.GlideHelper;
import com.aj.jav.service.BaseDomain;
import com.aj.jav.utils.ValueUtility;

import java.util.List;
import java.util.Map;

/**
 * Created by pascal on 2018/3/27.
 * 長片1欄/2欄/短片2欄 的VideoHolder
 */

public class VideoHolder extends RecyclerView.ViewHolder implements MainListContract.RepositoryRowView{

    private int mFilmType;
    private Context mContext;

    private TextView textDate;
    private TextView textTitle;
    private TextView textActor;
    private TextView textMainTag;
    private TextView textSecondTag1;
    private TextView textSecondTag2;
    private ImageView imageCover;
    private ToggleButton toggleLike;

    private boolean isCN;

    public VideoHolder(Context context, View itemView, int filmType) {
        super(itemView);
        mContext = context;
        mFilmType = filmType;

        isCN = ValueUtility.isCN();

        textDate = itemView.findViewById(R.id.text_date);
        textTitle = itemView.findViewById(R.id.text_title);
        textActor = itemView.findViewById(R.id.text_actor);
        imageCover = itemView.findViewById(R.id.image_cover);
        textMainTag = itemView.findViewById(R.id.text_main_tag);
        textSecondTag1 = itemView.findViewById(R.id.text_second_tag_1);
        textSecondTag2 = itemView.findViewById(R.id.text_second_tag_2);
        toggleLike = itemView.findViewById(R.id.toggle_like);

    }

    private int placeHolderId() {
        switch (mFilmType) {
            case Constant.DISPLAY_TYPE_LONG_2:
                return R.drawable.ic_image_default_straight;
            case Constant.DISPLAY_TYPE_LONG_1:
            case Constant.DISPLAY_TYPE_SHORT_2:
                return R.drawable.ic_image_default_horizontal;
            default:
                return R.drawable.ic_image_default_horizontal;
        }
    }

    public void onBindViewHolder(VideoHolder videoHolder, final int position, List<Map<String, Object>> dataList) {
        switch (mFilmType) {
            case Constant.DISPLAY_TYPE_LONG_1:
            case Constant.DISPLAY_TYPE_LONG_2:
                videoHolder.textDate.setText(ValueUtility.getDate((long) dataList.get(position).get("date")));
                break;
            case Constant.DISPLAY_TYPE_SHORT_2:
                videoHolder.textDate.setText(ValueUtility.getTime(String.valueOf((int) dataList.get(position).get("duration"))));
                break;
        }

        videoHolder.textTitle.setText((String) dataList.get(position).get("title"));
        videoHolder.textActor.setText((String) dataList.get(position).get("actor"));

        GlideHelper.setImage(mContext, BaseDomain.sBaseImageDomain + dataList.get(position).get("cover_url"), BaseDomain.sBaseImageReferer, placeHolderId(), imageCover);

        /* Main Tag */
        String mainTag = (String) dataList.get(position).get("main_tag");
        if (mainTag.equals("")) {
            videoHolder.textMainTag.setVisibility(View.INVISIBLE);
        } else if (mainTag.equals("限免")) {
            videoHolder.textMainTag.setVisibility(View.VISIBLE);
            videoHolder.textMainTag.setBackground(mContext.getResources().getDrawable(R.drawable.bg_gradient_main_tag_orange));
        } else {
            videoHolder.textMainTag.setVisibility(View.VISIBLE);
            videoHolder.textMainTag.setBackground(mContext.getResources().getDrawable(R.drawable.bg_gradient_main_tag_red));
        }

        videoHolder.textMainTag.setText(transparentString((String) dataList.get(position).get("main_tag")));

        /* Second Tag */
//
        if (dataList.get(position).get("second_tag") == null) {
            videoHolder.textSecondTag1.setVisibility(View.GONE);
            videoHolder.textSecondTag2.setVisibility(View.GONE);
        } else {
            List<String> secondTags = (List<String>) dataList.get(position).get("second_tag");

            if (secondTags.size() == 2) {
                videoHolder.textSecondTag1.setVisibility(View.VISIBLE);
                videoHolder.textSecondTag2.setVisibility(View.VISIBLE);
            } else if (secondTags.size() == 1) {
                if (secondTags.get(0).equals("中")) {
                    videoHolder.textSecondTag1.setVisibility(View.VISIBLE);
                    videoHolder.textSecondTag2.setVisibility(View.GONE);
                } else if (secondTags.get(0).equals("無")) {
                    videoHolder.textSecondTag1.setVisibility(View.GONE);
                    videoHolder.textSecondTag2.setVisibility(View.VISIBLE);
                }
            } else {
                videoHolder.textSecondTag1.setVisibility(View.GONE);
                videoHolder.textSecondTag2.setVisibility(View.GONE);
            }
        }

        /* Like */
        final String id = (String) dataList.get(position).get("id");
        final boolean like = (boolean) dataList.get(position).get("like");
        videoHolder.toggleLike.setChecked(like);
        videoHolder.toggleLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mFilmPresenter.likeVideo(id, !like, position);
            }
        });

        videoHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mContext instanceof FilmActivity){
//                    ((FilmActivity) mContext).finish();
//                }
//                Bundle bundle = new Bundle();
//                bundle.putString("video_id", id);
//                bundle.putBoolean("like", like);
//                bundle.putInt(Constant.FILM_RECYCLE_ITEM_TYPE, mFilmType);
//                ViewUtility.gotoNextActivity(mContext, FilmActivity.class, bundle);
//
//                setGA(id);
            }
        });
    }

    /**
     * @param id 影片id
     */
    private void setGA(String id) {
        switch (mFilmType) {
            case Constant.DISPLAY_TYPE_LONG_1:
            case Constant.DISPLAY_TYPE_LONG_2:
//                GaHelper.getInstance().setTrackEvents("LongFilmListPage.action", "GoToLongFilmPlayPage", id);
                break;
            case Constant.DISPLAY_TYPE_SHORT_2:
//                GaHelper.getInstance().setTrackEvents("ShortFilmListPage.action", "GoToShortFilmPlayPage", id);
                break;
        }
    }

    private String transparentString(String s) {
        if (!isCN) return s;
        switch (s) {
            case "獨家":
                return "独家";
            default:
                return s;
        }
    }

    @Override
    public void setTitle(String title) {
        Log.i("ddd","set "+title);
    }

    @Override
    public void setStarCount(int starCount) {

    }
}