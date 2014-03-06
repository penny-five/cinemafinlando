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

import android.content.Intent;
import android.os.Bundle;

import com.github.pennyfive.finnkino.FinnkinoIntents;
import com.github.pennyfive.finnkino.api.model.Event;
import com.github.pennyfive.finnkino.api.model.TheatreArea;
import com.github.pennyfive.finnkino.util.Fragments;

public class EventListActivity extends DrawerActivity implements TheatreAreaFragment.Callbacks, EventListFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentFragment(new EventListFragment());
        setDrawerFragment(new TheatreAreaFragment());
    }

    @Override
    public void onTheatreAreaSelected(TheatreArea area) {
        Bundle args = new Bundle();
        args.putParcelable(FinnkinoIntents.EXTRA_THEATRE_AREA, area);
        setContentFragment(Fragments.instantiateWithArgs(EventListFragment.class, args));

        closeDrawer();
    }

    @Override
    public void onEventSelected(Event event) {
        Intent intent = new Intent(FinnkinoIntents.ACTION_VIEW_EVENT);
        intent.putExtra(FinnkinoIntents.EXTRA_EVENT, event);
        startActivity(intent);
    }
}
