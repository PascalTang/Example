/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aj.jav.room.ui;

import android.arch.lifecycle.ViewModel;

import com.aj.jav.data_model.MainListGson;
import com.aj.jav.room.dao.MainListEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;

public class MainListViewModel extends ViewModel {

    private final MainListDataSource mDataSource;

    public MainListViewModel(MainListDataSource dataSource) {
        mDataSource = dataSource;
    }

    public int getSize() {
        return mDataSource.getSize();
    }

    public Flowable<List<MainListEntity>> getMainList() {
        return mDataSource.getMainList()
                .map(mainListEntities -> mainListEntities);
    }

    public Single<Boolean> getMainList(String videoId) {
        return mDataSource.getVideoLike(videoId)
                .map(like -> like);
    }

    public Completable insertMainList(MainListGson gson) {
        return Completable.fromAction(() -> {
            List<MainListEntity> mainList = new ArrayList<>();
            for (int i = 0 ; i < gson.getResponse().getVideos().size() ; i++){
                mainList.add(new MainListEntity(gson.getResponse().getVideos().get(i).getVideo_id(), gson.getResponse().getVideos().get(i).isVideo_like()));
            }
            mDataSource.insertMainList(mainList);
        });
    }

    /**
     * mDomainTables 因為insert時會更新
     * 若delete時不去清除他
     * 下次insert時會繼續add進去
     */
    public Observable<Object> deleteTable() {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                mDataSource.deleteTable();
                e.onNext(0);
            }
        });
    }

    public Completable updateLike(String videoId , boolean like) {
        return Completable.fromAction(() -> {
            mDataSource.updateLike(videoId,like);
        });
    }
}
