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
import android.view.View;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

/**
 *
 */
public class CinemaFinlandoApplication extends Application {
    private static CinemaFinlandoApplication instance;

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        createObjectGraph();
    }

    private void createObjectGraph() {
        objectGraph = ObjectGraph.create(new CinemaFinlandoModule(this));
    }

    private void inject(Object o) {
        objectGraph.inject(o);
    }

    public static class InjectUtils {

        public static void injectMembers(Object o) {
            instance.inject(o);
        }

        public static void injectViews(Object o, View view) {
            ButterKnife.inject(o, view);
        }

        public static void injectAll(Object o, View view) {
            injectMembers(o);
            injectViews(o, view);
        }

        public static void injectAll(Activity activity) {
            instance.inject(activity);
            ButterKnife.inject(activity);
        }
    }
}
