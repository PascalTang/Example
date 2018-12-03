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

import com.aj.jav.room.dao.MainListEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Access point for managing user data.
 */
public interface MainListDataSource {

    int getSize();

    void deleteTable();

    void insertMainList(List<MainListEntity> mainList);

    Single<Boolean> getVideoLike(String id);

    void updateLike(String id , boolean like);

    Flowable<List<MainListEntity>> getMainList();
}