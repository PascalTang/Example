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

package com.aj.jav.room.dao;

import com.aj.jav.room.ui.MainListDataSource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Using the Room database as a data source.
 */
public class MainListRepository implements MainListDataSource {

    private final MainListDao mMainListDao;

    public MainListRepository(MainListDao videoLikeDao) {
        mMainListDao = videoLikeDao;
    }

    @Override
    public int getSize() {
        return mMainListDao.getSize();
    }

    @Override
    public void deleteTable() {
        mMainListDao.deleteTable();
    }

    @Override
    public void insertMainList(List<MainListEntity> mainList) {
        mMainListDao.insertMainList(mainList);
    }

    @Override
    public Single<Boolean> getVideoLike(String id) {
        return mMainListDao.getVideoLike(id);
    }

    @Override
    public Flowable<List<MainListEntity>> getMainList() {
        return mMainListDao.getMainList();
    }
}
