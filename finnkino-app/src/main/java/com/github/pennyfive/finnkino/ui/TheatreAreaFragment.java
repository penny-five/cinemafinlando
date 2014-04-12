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

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.pennyfive.finnkino.R;
import com.github.pennyfive.finnkino.api.model.TheatreArea;
import com.github.pennyfive.finnkino.api.model.TheatreAreas;
import com.github.pennyfive.finnkino.api.service.Command;
import com.github.pennyfive.finnkino.api.service.GetTheatreAreasCommand;

/**
 *
 */
public class TheatreAreaFragment extends QueryListFragment<TheatreArea, TheatreAreas> {

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addHeaderView(UiUtils.inflateWithText(getActivity(), R.layout.item_drawer, R.string.drawer_item_now_showing));
        addHeaderView(UiUtils.inflateWithText(getActivity(), R.layout.item_drawer, R.string.drawer_item_coming_soon));
        addHeaderView(UiUtils.inflateWithText(getActivity(), R.layout.item_drawer_title, R.string.drawer_divider_cinemas), null, false);
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
        /* Uses different views for normal theatre areas and sub-theatre areas */
        return 2;
    }

    @Override
    protected int getItemViewType(int position) {
        return getItem(position).isChildArea() ? 1 : 0;
    }

    @Override
    protected void onHeaderClick(int position, View view) {
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

    @Override
    protected void onItemClick(int position, View view, TheatreArea area) {
        ((Callbacks) getActivity()).onTheatreAreaSelected(area);
    }
}
