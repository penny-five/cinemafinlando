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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.TheatreArea;
import com.github.pennyfive.cinemafinlando.api.model.TheatreAreas;
import com.github.pennyfive.cinemafinlando.api.service.Command;
import com.github.pennyfive.cinemafinlando.api.service.GetTheatreAreasCommand;

/**
 *
 */
public class TheatreAreaFragment extends QueryAbsListFragment<TheatreArea, TheatreAreas> {
    private static final int NUM_HEADERS = 3;

    public interface Callbacks {
        void onTheatreAreaSelected(TheatreArea area);

        void onUpcomingMoviesSelected();

        void onNowPlayingMoviesSelected();
    }

    @Override
    protected Command<TheatreAreas> onCreateCommand() {
        return new GetTheatreAreasCommand();
    }

    @Override
    protected AbsListView createAbsListView() {
        ListView view = new ListView(getActivity());
        view.setDividerHeight(0);
        view.addHeaderView(inflateHeader(R.layout.item_drawer, R.string.drawer_item_now_showing));
        view.addHeaderView(inflateHeader(R.layout.item_drawer, R.string.drawer_item_coming_soon));
        view.addHeaderView(inflateHeader(R.layout.item_drawer_title, R.string.drawer_divider_cinemas), null, false);
        return view;
    }

    private View inflateHeader(int layoutResid, int textResid) {
        return UiUtils.inflateWithText(getActivity(), layoutResid, textResid);
    }

    @Override
    protected View newView(Context context, LayoutInflater inflater, TheatreArea area) {
        int resid = area.isChildArea() ? R.layout.item_drawer_sub : R.layout.item_drawer;
        return inflater.inflate(resid, null);
    }

    @Override
    protected void bindView(Context context, View view, TheatreArea item) {
        ((TextView) view.findViewById(R.id.text)).setText(item.getName());
    }

    @Override
    protected int getViewTypeCount() {
        /* Uses different views for normal theatre areas and child theatre areas */
        return 2;
    }

    @Override
    protected int getItemViewType(int position) {
        return getItem(position).isChildArea() ? 1 : 0;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < NUM_HEADERS) {
            onHeaderClick(position);
        } else {
            onItemClick(position - NUM_HEADERS);
        }
    }

    private void onHeaderClick(int position) {
        switch (position) {
            case 0:
                ((Callbacks) getActivity()).onNowPlayingMoviesSelected();
                break;
            case 1:
                ((Callbacks) getActivity()).onUpcomingMoviesSelected();
                break;
            default:
                throw new IllegalStateException("pos: " + position);
        }
    }

    private void onItemClick(int position) {
        ((Callbacks) getActivity()).onTheatreAreaSelected(getItem(position));
    }
}
