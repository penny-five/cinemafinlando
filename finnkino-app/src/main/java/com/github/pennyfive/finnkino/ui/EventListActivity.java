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
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ListView;

import com.github.pennyfive.finnkino.FinnkinoApplication.InjectUtils;
import com.github.pennyfive.finnkino.FinnkinoIntents;
import com.github.pennyfive.finnkino.R;
import com.github.pennyfive.finnkino.api.GetNowInTheatresCommand;
import com.github.pennyfive.finnkino.api.model.Event;
import com.github.pennyfive.finnkino.api.model.Events;

import butterknife.InjectView;
import butterknife.OnItemClick;

public class EventListActivity extends Activity implements LoaderManager.LoaderCallbacks<Events> {
    @InjectView(R.id.list) ListView listView;
    private EventListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_showing);
        InjectUtils.inject(this);
        getLoaderManager().initLoader(0, savedInstanceState, this);
    }

    @Override
    public Loader<Events> onCreateLoader(int id, Bundle args) {
        return new ApiQueryLoader<>(this, new GetNowInTheatresCommand());
    }

    @Override
    public void onLoadFinished(Loader<Events> loader, Events events) {
        adapter = new EventListAdapter(this, events.getEvents());
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Events> loader) {

    }

    @OnItemClick(R.id.list)
    public void onEventClicked(int position) {
        Event event = adapter.getItem(position);
        Intent intent = new Intent(FinnkinoIntents.ACTION_VIEW_EVENT);
        intent.putExtra(FinnkinoIntents.EXTRA_EVENT, event);
        startActivity(intent);
    }

}
