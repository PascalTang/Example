package com.aj.jav.room.dao;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = TableName.MAIN_LIST)
public class MainListEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String videoId;
    private boolean like;

    public MainListEntity(String videoId, boolean like) {
        this.videoId = videoId;
        this.like = like;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}