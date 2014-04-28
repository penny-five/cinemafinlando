package com.github.pennyfive.cinemafinlando.ui;

import android.view.View;

import com.github.pennyfive.cinemafinlando.api.model.Event;
import com.github.pennyfive.cinemafinlando.api.model.Events;
import com.github.pennyfive.cinemafinlando.api.service.Command;
import com.github.pennyfive.cinemafinlando.api.service.GetEventsCommand;

/**
 * Shows list of movies that are now shown in the selected theatre.
 */
public class NowInTheatresListFragment extends BaseListFragment<Event, Events> {

    @Override
    protected Command<Events> onCreateCommand() {
        return GetEventsCommand.nowInTheatres();
    }

    @Override
    protected void onNewView(View view) {

    }

    @Override
    protected void onViewBound(View view, Event item) {

    }
}
