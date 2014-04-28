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
import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.Base;
import com.github.pennyfive.cinemafinlando.api.model.TheatreArea;

public class EventListActivity extends DrawerActivity implements NavigationFragment.Callbacks, BaseListFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            showFragment(new NowInTheatresListFragment(), getString(R.string.now_showing));
            setDrawerFragment(new NavigationFragment());
        }
    }

    @Override
    public void onTheatreAreaSelected(TheatreArea area) {
        Bundle args = new Bundle();
        args.putParcelable(CinemaFinlandoIntents.EXTRA_THEATRE_AREA, area);
        showFragment(UiUtils.instantiateWithArgs(TheatreAreaScheduleFragment.class, args), area.getName());
    }

    @Override
    public void onUpcomingMoviesSelected() {
        showFragment(new ComingSoonListFragment(), getString(R.string.coming_soon));
    }

    @Override
    public void onNowPlayingMoviesSelected() {
        showFragment(new NowInTheatresListFragment(), getString(R.string.now_showing));
    }

    private void showFragment(Fragment fragment, String actionBarTitle) {
        setContentFragment(fragment);
        setActionBarTitle(actionBarTitle);
        closeDrawer();
    }

    @Override
    public void onBaseSelected(Base base) {
        Intent intent = new Intent(CinemaFinlandoIntents.ACTION_VIEW_EVENT);
        intent.putExtra(EventActivity.EXTRA_IMAGES, base.getImages());
        intent.putExtra(EventActivity.EXTRA_GENRES, base.getGenres());
        intent.putExtra(EventActivity.EXTRA_TITLE, base.getTitle());
        intent.putExtra(EventActivity.EXTRA_ORIGINAL_TITLE, base.getOriginalTitle());
        startActivity(intent);
    }
}
