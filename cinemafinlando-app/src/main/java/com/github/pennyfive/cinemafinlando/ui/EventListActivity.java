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

package com.github.pennyfive.cinemafinlando.ui;

import android.os.Bundle;

import com.github.pennyfive.cinemafinlando.CinemaFinlandoIntents;
import com.github.pennyfive.cinemafinlando.api.model.Event;
import com.github.pennyfive.cinemafinlando.api.model.TheatreArea;

public class EventListActivity extends DrawerActivity implements TheatreAreaFragment.Callbacks, EventListFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = new Bundle();
        args.putString(EventListFragment.EXTRA_LIST_TYPE, EventListFragment.LIST_TYPE_NOW_IN_THEATRES);
        setContentFragment(UiUtils.instantiateWithArgs(EventListFragment.class, args));

        setDrawerFragment(new TheatreAreaFragment());
    }

    @Override
    public void onTheatreAreaSelected(TheatreArea area) {
        Bundle args = new Bundle();
        args.putParcelable(CinemaFinlandoIntents.EXTRA_THEATRE_AREA, area);
        args.putString(EventListFragment.EXTRA_LIST_TYPE, EventListFragment.LIST_TYPE_THEATRE_AREA);
        showEventListFragmentWithArgs(args);
    }

    @Override
    public void onUpcomingMoviesSelected() {
        Bundle args = new Bundle();
        args.putString(EventListFragment.EXTRA_LIST_TYPE, EventListFragment.LIST_TYPE_COMING_SOON);
        showEventListFragmentWithArgs(args);
    }

    @Override
    public void onNowPlayingMoviesSelected() {
        Bundle args = new Bundle();
        args.putString(EventListFragment.EXTRA_LIST_TYPE, EventListFragment.LIST_TYPE_NOW_IN_THEATRES);
        showEventListFragmentWithArgs(args);
    }

    private void showEventListFragmentWithArgs(Bundle args) {
        setContentFragment(UiUtils.instantiateWithArgs(EventListFragment.class, args));
        closeDrawer();
    }

    @Override
    public void onEventSelected(Event event) {
        //Intent intent = new Intent(CinemaFinlandoIntents.ACTION_VIEW_EVENT);
        //intent.putExtra(CinemaFinlandoIntents.EXTRA_EVENT, event);
        //startActivity(intent);
    }
}
