/*
 * Copyright 2014 Joonas Lehtonen
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

package com.github.pennyfive.cinemafinlando.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;

import com.github.pennyfive.cinemafinlando.CinemaFinlandoApplication.InjectUtils;
import com.github.pennyfive.cinemafinlando.QueryLanguagePreferenceChangedEvent;
import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.ui.activity.AboutActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

/**
 *
 */
public class SettingsFragment extends PreferenceFragment {
    @Inject Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectMembers(this);
        bus.register(this);

        addPreferencesFromResource(R.xml.prefs);

        findPreference(getString(R.string.pref_about_key)).setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Subscribe
    public void onQueryLanguagePreferenceChanged(QueryLanguagePreferenceChangedEvent event) {
        refreshQueryLanguagePreference();
    }

    private void refreshQueryLanguagePreference() {
        ListPreference preference = (ListPreference) findPreference(getString(R.string.pref_query_language_key));
        if (preference != null) {
            preference.setSummary(preference.getEntry());
        }
    }
}
