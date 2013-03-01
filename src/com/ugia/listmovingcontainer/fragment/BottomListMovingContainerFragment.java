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

package com.ugia.listmovingcontainer.fragment;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ugia.listmovingcontainer.R;
import com.ugia.listmovingcontainer.adapter.ListAdapter;
import com.ugia.listmovingcontainer.utils.DrawingUtils;

public class BottomListMovingContainerFragment extends Fragment {

    private static final int FX_FACTOR = 2;

    private ArrayList<String> fakeData;

    private ImageView mShadow;
    private RelativeLayout mainView;
    private RelativeLayout mBottomListViewContainer;
    private ListView mMainListView;
    private ListAdapter mAdapter;
    private View mFakeFooter;

    private boolean mEnableFX;
    private boolean mEnableParalax;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_test_list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

        getPrefs();
        fetchData();
        init();
    }

    /**
     * Variable initialization
     */
    private void init() {
        bindViews();

        mFakeFooter.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int) getResources().getDimension(
                R.dimen.moving_view_height)
                + (int) getResources().getDimension(R.dimen.moving_view_vs_list_margin)));

        mMainListView.addFooterView(mFakeFooter);
        mMainListView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        updateFooter();
                    }
                });

        mMainListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                updateFooter();
            }
        });

        if (mAdapter == null) {
            mAdapter = new ListAdapter(getActivity(), fakeData);
        }

        mMainListView.setAdapter(mAdapter);
    }

    /**
     * View binding
     */
    private void bindViews() {
        mShadow = (ImageView) getActivity().findViewById(R.id.shadow);
        mShadow.setVisibility(mEnableFX ? View.VISIBLE : View.GONE);
        mainView = (RelativeLayout) getActivity().findViewById(R.id.mainView);
        mBottomListViewContainer = (RelativeLayout) getActivity().findViewById(R.id.bottomListViewContainer);
        mMainListView = (ListView) getActivity().findViewById(R.id.mainListView);

        mFakeFooter = new View(getActivity());
    }

    /**
     * Get current value for preferences
     */
    private void getPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEnableFX = prefs.getBoolean("container_effects_preference", false);
        mEnableParalax = prefs.getBoolean("container_parallax_preference", false);
    }

    /**
     * Initialize / reset set of data
     */
    private void fetchData() {
        fakeData = new ArrayList<String>();
        fakeData.add("1");
        fakeData.add("2");
        fakeData.add("3");
        fakeData.add("4");
        fakeData.add("5");
        fakeData.add("6");
    }

    /**
     * Adds a new element to the set of the data and makes sure the adapter is refreshed
     *
     * @param  v  View from which the method was called (Button)
     */
    public void addElement(View v) {
        if (fakeData == null) {
            fetchData();
        }

        fakeData.add(Integer.toString(fakeData.size() + 1));
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Refreshes the state and position of the footer. This method is intended to be called any time
     * the scroll position (or any other dependent object) changes in order to apply UI transformations to the
     * target container accordingly
     */
    private void updateFooter() {

        int mBottomListViewContainerHeight = mBottomListViewContainer.getMeasuredHeight();
        float shadowHeight = getResources().getDimension(R.dimen.moving_view_shadow_height);
        float listVsMovingViewMargin = getResources().getDimension(R.dimen.moving_view_vs_list_margin);
        int emptyHeight = mainView.getMeasuredHeight() - mFakeFooter.getTop();

        int offsetY = (mFakeFooter != null && mFakeFooter.isShown() ? 
                mEnableParalax ?
                -(mBottomListViewContainerHeight + (int) listVsMovingViewMargin - emptyHeight) * mBottomListViewContainerHeight
                / (mBottomListViewContainerHeight + (int) listVsMovingViewMargin) * 2
                : emptyHeight - mBottomListViewContainerHeight
                : -mBottomListViewContainerHeight)
                + (mEnableFX ? (int) shadowHeight : 0);

        mBottomListViewContainer.scrollTo(0, Math.min(0, offsetY));

        if (mEnableFX) {

            float sOffset = Math.max(offsetY, -listVsMovingViewMargin - shadowHeight * shadowHeight
                    / listVsMovingViewMargin);

            int alpha = offsetY > 0 ?
                    255 
                    : 255 - (int) shadowHeight + Math.max(offsetY * FX_FACTOR, -mBottomListViewContainerHeight) * 255
                    / mBottomListViewContainerHeight;

            // Alpha FX
            DrawingUtils.onSetAlpha(Math.max(alpha, 0), mBottomListViewContainer);

            // Shadow FX
            mShadow.scrollTo(0, Math.min(0, (int) sOffset));
        }
    }
}
