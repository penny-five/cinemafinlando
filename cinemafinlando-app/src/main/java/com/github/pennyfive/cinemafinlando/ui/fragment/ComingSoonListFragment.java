package com.github.pennyfive.cinemafinlando.ui.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEvent;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEventContainer;
import com.github.pennyfive.cinemafinlando.api.service.Command;
import com.github.pennyfive.cinemafinlando.api.service.GetEventsCommand;
import com.github.pennyfive.cinemafinlando.ui.activity.generic.DrawerActivity;
import com.github.pennyfive.cinemafinlando.ui.fragment.generic.EventListFragment;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Comparator;

/**
 * Shows list of movies that are coming soon to cinemas.
 */
public class ComingSoonListFragment extends EventListFragment {
    private static final DateTimeFormatter RELEASE_DATE_FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy");
    private static final Comparator NAME_COMPARATOR = new Comparator<DetailedEvent>() {

        @Override
        public int compare(DetailedEvent lhs, DetailedEvent rhs) {
            return lhs.getTitle().compareTo(rhs.getTitle());
        }
    };
    private static final Comparator DATE_COMPARATOR = new Comparator<DetailedEvent>() {

        @Override
        public int compare(DetailedEvent lhs, DetailedEvent rhs) {
            return lhs.getReleaseDate().compareTo(rhs.getReleaseDate());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        sort(DATE_COMPARATOR);
        ((DrawerActivity) getActivity()).setActionBarNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        ((DrawerActivity) getActivity()).setActionBarTitle(getString(R.string.nav_drawer_title_coming_soon));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.coming_soon, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_date:
                sort(DATE_COMPARATOR);
                return true;
            case R.id.sort_by_name:
                sort(NAME_COMPARATOR);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
