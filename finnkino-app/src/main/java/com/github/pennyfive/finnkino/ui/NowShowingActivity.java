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

package com.github.pennyfive.finnkino.ui;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ListView;

import com.github.pennyfive.finnkino.R;
import com.github.pennyfive.finnkino.api.GetScheduleCommand;
import com.github.pennyfive.finnkino.api.model.Schedule;
import com.github.pennyfive.finnkino.util.ApiQueryLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NowShowingActivity extends Activity implements LoaderManager.LoaderCallbacks<Schedule> {
    @InjectView(R.id.list) ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_showing);
        ButterKnife.inject(this);
        getLoaderManager().initLoader(0, savedInstanceState, this);
    }

    @Override
    public Loader<Schedule> onCreateLoader(int id, Bundle args) {
        return new ApiQueryLoader<Schedule>(this, new GetScheduleCommand());
    }

    @Override
    public void onLoadFinished(Loader<Schedule> loader, Schedule schedule) {
        listView.setAdapter(new ShowAdapter(this, schedule.getShows()));
    }

    @Override
    public void onLoaderReset(Loader<Schedule> loader) {

    }

}
