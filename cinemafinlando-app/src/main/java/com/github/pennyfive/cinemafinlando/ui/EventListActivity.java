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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.pennyfive.cinemafinlando.CinemaFinlandoIntents;
import com.github.pennyfive.cinemafinlando.api.model.Event;
import com.github.pennyfive.cinemafinlando.api.model.TheatreArea;

public class EventListActivity extends DrawerActivity implements NavigationFragment.Callbacks, EventCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setContentFragment(new NowShowingListFragment());
            setDrawerFragment(new NavigationFragment());
        }
    }

    @Override
    public void onTheatreAreaSelected(TheatreArea area) {
        Bundle args = new Bundle();
        args.putParcelable(CinemaFinlandoIntents.EXTRA_THEATRE_AREA, area);
        setContentFragment(UiUtils.instantiateWithArgs(TheatreAreaScheduleFragment.class, args));
    }

    @Override
    public void onComingSoonSelected() {
        setContentFragment(new ComingSoonListFragment());
    }

    @Override
    public void onNowShowingSelected() {
        setContentFragment(new NowShowingListFragment());
    }

    protected void setContentFragment(Fragment fragment) {
        super.setContentFragment(fragment);
        closeDrawer();
    }

    @Override
    public void onEventSelected(Event event) {
        Intent intent = new Intent(CinemaFinlandoIntents.ACTION_VIEW_EVENT);
        intent.putExtra(CinemaFinlandoIntents.EXTRA_EVENT_ID, event.getId());
        intent.putExtra(CinemaFinlandoIntents.EXTRA_TITLE, event.getTitle());
        intent.putExtra(CinemaFinlandoIntents.EXTRA_ORIGINAL_TITLE, event.getOriginalTitle());
        intent.putExtra(CinemaFinlandoIntents.EXTRA_LENGTH, event.getLengthInMinutes());
        intent.putExtra(CinemaFinlandoIntents.EXTRA_IMAGES, event.getImages());
        intent.putExtra(CinemaFinlandoIntents.EXTRA_GENRES, event.getGenres());
        startActivity(intent);
    }
}
