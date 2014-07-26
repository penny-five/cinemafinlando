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

package com.github.pennyfive.cinemafinlando.ui.activity;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

import com.github.pennyfive.cinemafinlando.BuildConfig;
import com.github.pennyfive.cinemafinlando.R;

/**
 *
 */
public class VersionPreference extends Preference {

    public VersionPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VersionPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VersionPreference(Context context) {
        super(context);
    }

    @Override
    public CharSequence getTitle() {
        return getContext().getString(R.string.pref_title_application_version);
    }

    @Override
    public CharSequence getSummary() {
        return BuildConfig.VERSION_NAME;
    }
}
