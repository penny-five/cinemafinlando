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

package com.github.pennyfive.cinemafinlando.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.TheatreArea;
import com.github.pennyfive.cinemafinlando.api.model.TheatreAreaContainer;
import com.github.pennyfive.cinemafinlando.api.service.Command;
import com.github.pennyfive.cinemafinlando.api.service.GetTheatreAreasCommand;
import com.github.pennyfive.cinemafinlando.ui.UiUtils;
import com.github.pennyfive.cinemafinlando.ui.activity.SettingsActivity;
import com.github.pennyfive.cinemafinlando.ui.fragment.generic.QueryAbsListFragment;

/**
 * Contents for navigation drawer. Contains following options:
 * <ul>
 * <li>View movies that are now playing in cinemas</li>
 * <li>View movies that are coming soon to cinemas</li>
 * <li>View movies for cities or individual theatres</li>
 * <li>Open Settings menu</li>
 * </ul>
 */
public class NavigationFragment extends QueryAbsListFragment<TheatreArea, TheatreAreaContainer> {
    private static final int NUM_HEADERS = 3;

    public interface Callbacks {
        void onTheatreAreaSelected(TheatreArea area);

        void onComingSoonSelected();

        void onNowShowingSelected();
    }

    @Override
    protected Command<TheatreAreaContainer> onCreateCommand() {
        return new GetTheatreAreasCommand(UiUtils.getQueryLanguage(getActivity()));
    }

    @Override
    protected AbsListView createAbsListView() {
        ListView view = new ListView(getActivity());
        view.setSelector(R.drawable.list_selector);
        view.setDrawSelectorOnTop(true);
        view.setDividerHeight(0);
        view.addHeaderView(View.inflate(getActivity(), R.layout.item_drawer_now_showing, null));
        view.addHeaderView(View.inflate(getActivity(), R.layout.item_drawer_coming_soon, null));
        view.addHeaderView(View.inflate(getActivity(), R.layout.item_drawer_cinemas_divider, null));
        view.addFooterView(View.inflate(getActivity(), R.layout.footer_drawer_settings, null));
        return view;
    }

    @Override
    protected View newView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.item_drawer, parent, false);
    }

    @Override
    protected void bindView(View view, TheatreArea item, int position) {
        ((TextView) view.findViewById(R.id.text)).setText(item.getName());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < NUM_HEADERS) {
            onHeaderClick(position);
        } else if (position == NUM_HEADERS + getItemCount()) {
            onFooterClick();
        } else {
            onItemClick(position - NUM_HEADERS);
        }
    }

    private void onHeaderClick(int position) {
        switch (position) {
            case 0:
                ((Callbacks) getActivity()).onNowShowingSelected();
                break;
            case 1:
                ((Callbacks) getActivity()).onComingSoonSelected();
                break;
            default:
                throw new IllegalStateException("pos: " + position);
        }
    }

    private void onFooterClick() {
        startActivity(new Intent(getActivity(), SettingsActivity.class));
    }

    private void onItemClick(int position) {
        ((Callbacks) getActivity()).onTheatreAreaSelected(getItem(position));
    }
}
