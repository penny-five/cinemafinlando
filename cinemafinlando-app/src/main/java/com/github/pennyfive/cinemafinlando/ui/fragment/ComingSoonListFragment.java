package com.github.pennyfive.cinemafinlando.ui.fragment;

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
import com.github.pennyfive.cinemafinlando.ui.UiUtils;
import com.github.pennyfive.cinemafinlando.ui.fragment.generic.EventListFragment;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Comparator;

/**
 * Shows list of movies that are coming soon to cinemas.
 */
public class ComingSoonListFragment extends EventListFragment {
    private static final String BUNDLE_KEY_SORT_BY_NAME = "sort_by_name";

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

    private boolean isSortedByName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.getBoolean(BUNDLE_KEY_SORT_BY_NAME, false)) {
            sort(NAME_COMPARATOR);
        } else {
            sort(DATE_COMPARATOR);
        }

        setHasOptionsMenu(true);

        //((DrawerActivity) getActivity()).setActionBarTitle(getString(R.string.nav_drawer_title_coming_soon));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_KEY_SORT_BY_NAME, getSortComparator() == NAME_COMPARATOR);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.coming_soon, menu);
        if (isSortedByName) {
            menu.findItem(R.id.menu_order_by_name).setChecked(true);
        } else {
            menu.findItem(R.id.menu_order_by_date).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_order_by_date:
                sort(DATE_COMPARATOR);
                item.setChecked(true);
                return true;
            case R.id.menu_order_by_name:
                sort(NAME_COMPARATOR);
                item.setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Command<DetailedEventContainer> onCreateCommand() {
        return GetEventsCommand.comingSoon(UiUtils.getQueryLanguage(getActivity()));
    }

    @Override
    protected void sort(Comparator<DetailedEvent> comparator) {
        super.sort(comparator);
        isSortedByName = comparator == NAME_COMPARATOR;
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
