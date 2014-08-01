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

package com.github.pennyfive.cinemafinlando;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.view.View;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

/**
 *
 */
public class CinemaFinlandoApplication extends Application implements OnSharedPreferenceChangeListener {
    private static CinemaFinlandoApplication instance;

    private ObjectGraph objectGraph;
    @Inject Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (userHasAllowedAnalyticsUsage()) {
            Analytics.start(this);
        }

        createObjectGraph();
        registerPreferenceListener();
    }

    private boolean userHasAllowedAnalyticsUsage() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getBoolean(getString(R.string.pref_analytics_key), true);
    }

    private void createObjectGraph() {
        objectGraph = ObjectGraph.create(new CinemaFinlandoModule(this));
        objectGraph.inject(this);
    }

    private void registerPreferenceListener() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    private void inject(Object o) {
        objectGraph.inject(o);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_query_language_key))) {
            bus.post(new QueryLanguagePreferenceChangedEvent());
        }
    }

    public static class InjectUtils {

        public static void injectMembers(Object o) {
            instance.inject(o);
        }

        public static void injectViews(Object o, View view) {
            ButterKnife.inject(o, view);
        }

        public static void injectViews(Activity activity) {
            ButterKnife.inject(activity);
        }
    }
}
