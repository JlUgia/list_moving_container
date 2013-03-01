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

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

import com.ugia.listmovingcontainer.R;

public class InitSelectionActivity extends PreferenceActivity {

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        init();
    }

    /**
     * Initialization
     */
    private void init() {

        addPreferencesFromResource(R.xml.pref_general);

        Preference goPreference = findPreference("pref_go");
        if (goPreference != null) {
            goPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(getBaseContext(), MainFragmentActivity.class));
                    return false;
                }
            });
        }
    }
}
