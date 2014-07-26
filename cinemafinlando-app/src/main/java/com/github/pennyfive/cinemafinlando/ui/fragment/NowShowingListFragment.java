package com.github.pennyfive.cinemafinlando.ui.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;

import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEvent;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEventContainer;
import com.github.pennyfive.cinemafinlando.api.service.Command;
import com.github.pennyfive.cinemafinlando.api.service.GetEventsCommand;
import com.github.pennyfive.cinemafinlando.ui.UiUtils;
import com.github.pennyfive.cinemafinlando.ui.activity.generic.DrawerActivity;
import com.github.pennyfive.cinemafinlando.ui.fragment.generic.EventListFragment;

/**
 * Shows list of movies that are now shown in the selected theatre.
 */
public class NowShowingListFragment extends EventListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DrawerActivity) getActivity()).setActionBarNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        ((DrawerActivity) getActivity()).setActionBarTitle(getString(R.string.nav_drawer_title_now_showing));
    }

    @Override
    protected Command<DetailedEventContainer> onCreateCommand() {
        return GetEventsCommand.nowInTheatres(UiUtils.getQueryLanguage(getActivity()));
    }

    @Override
    protected void onNewView(View view) {

    }

    @Override
    protected void onViewBound(View view, DetailedEvent item) {

    }
}
