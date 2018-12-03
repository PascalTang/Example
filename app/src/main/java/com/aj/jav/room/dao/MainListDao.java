package com.aj.jav.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * true = 1 , false = 0 , null要用 is null 不是 =
 * onConflict = OnConflictStrategy.REPLACE 當 primary key相同時 會覆蓋
 */

@Dao
public interface MainListDao {

    @Query("SELECT COUNT(*) FROM MAIN_LIST")
    int getSize();

    @Query("DELETE FROM MAIN_LIST")
    void deleteTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMainList(List<MainListEntity> videosLikeEntity);

    @Query("SELECT `like` FROM MAIN_LIST WHERE id = :id ")
    Single<Boolean> getVideoLike(String id);

    @Query("UPDATE MAIN_LIST SET `like` = :like WHERE id = :id")
    void updateLike(String id , boolean like);

//    @Query("SELECT * FROM MAIN_LIST WHERE `like` = 1")
//    Flowable<List<MainListEntity>> getMainList();

    @Query("SELECT * FROM MAIN_LIST")
    Flowable<List<MainListEntity>> getMainList();
}