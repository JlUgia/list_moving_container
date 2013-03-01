/*
 * Copyright 2013 Jose L Ugia - @jl_ugia
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ugia.listmovingcontainer.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.ugia.listmovingcontainer.fragment.BottomListMovingContainerFragment;

public class MainFragmentActivity extends FragmentActivity {

    public static final String EXTRA_SELECTED_MODE = "extra_selected_mode";

    private BottomListMovingContainerFragment mBottomListMovingContainer;
    private int mSelectedMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            mSelectedMode = getIntent().getIntExtra(EXTRA_SELECTED_MODE, 0);
        }

        switch (mSelectedMode) {
        case 0:
            if (mBottomListMovingContainer == null) {
                mBottomListMovingContainer = new BottomListMovingContainerFragment();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, mBottomListMovingContainer).commit();
            break;

        case 1:

            break;
        }
    }

    /**
     * Adds a new element to the set of the data and makes sure the adapter is refreshed
     * 
     * @param v
     *            View from which the method was called (Button)
     */
    public void addElement(View v) {
        if (mBottomListMovingContainer != null) {
            mBottomListMovingContainer.addElement(v);
        }
    }
}