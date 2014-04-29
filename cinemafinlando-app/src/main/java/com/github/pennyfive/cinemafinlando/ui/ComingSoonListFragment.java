package com.github.pennyfive.cinemafinlando.ui;

import android.view.View;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEvent;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEventContainer;
import com.github.pennyfive.cinemafinlando.api.service.Command;
import com.github.pennyfive.cinemafinlando.api.service.GetEventsCommand;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Shows list of movies that are coming soon to cinemas.
 */
public class ComingSoonListFragment extends EventListFragment {
    private static final DateTimeFormatter RELEASE_DATE_FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy");

    @Override
    protected Command<DetailedEventContainer> onCreateCommand() {
        return GetEventsCommand.comingSoon();
    }

    @Override
    protected void onNewView(View view) {
        view.findViewById(R.id.date).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onViewBound(View view, DetailedEvent event) {
        ((TextView) view.findViewById(R.id.date)).setText(event.getReleaseDate().toString(RELEASE_DATE_FORMATTER));
    }
}
