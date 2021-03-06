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
import android.arch.lifecycle.ViewModelProvider;

import com.aj.jav.room.ui.MainListDataSource;
import com.aj.jav.room.ui.MainListViewModel;

/**
 * Factory for ViewModels
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private MainListDataSource mMainListDataSource;

    public ViewModelFactory(MainListDataSource mainListDataSource) {
        mMainListDataSource = mainListDataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainListViewModel.class)) {
            return (T) new MainListViewModel(mMainListDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
