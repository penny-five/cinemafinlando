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

package com.github.pennyfive.cinemafinlando.ui.fragment.generic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEvent;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEventContainer;
import com.github.pennyfive.cinemafinlando.api.model.Event;
import com.github.pennyfive.cinemafinlando.api.model.EventGallery;
import com.github.pennyfive.cinemafinlando.ui.EventCallbacks;
import com.github.pennyfive.cinemafinlando.ui.PaletteGeneratingTargetWrapper;

import java.util.List;

/**
 *
 */
public abstract class EventListFragment extends QueryAbsListFragment<DetailedEvent, DetailedEventContainer> {
    private static final String BUNDLE_KEY_CONTENT_SHOWN = "content_shown";
    private boolean contentShown = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            contentShown = savedInstanceState.getBoolean(BUNDLE_KEY_CONTENT_SHOWN);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_KEY_CONTENT_SHOWN, contentShown);
    }

    @Override
    protected AbsListView createAbsListView() {
        GridView grid = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.generic_gridview, null);
        grid.setSelector(R.drawable.list_framed_selector);
        grid.setNumColumns(getResources().getInteger(R.integer.event_list_columns));
        if (contentShown) {
            // Make sure that layout animation is not shown if the GridView is re-created (e.g. orientation change)
            grid.setLayoutAnimation(null);
        }
        return grid;
    }

    @Override
    protected View newView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_event, parent, false);
        onNewView(view);
        return view;
    }

    @Override
    protected void onStateChanged(int state, View view) {
        super.onStateChanged(state, view);
        if (state == STATE_LOADING) {
            contentShown = false;
        } else if (state == STATE_CONTENT) {
            contentShown = true;
        }
    }

    @Override
    protected void bindView(View view, DetailedEvent event, int position) {
        ((TextView) view.findViewById(R.id.text)).setText(event.getTitle());
        ImageView target = (ImageView) view.findViewById(R.id.image);
        String url = event.getImages().getUrl(EventGallery.SIZE_LANDSCAPE_LARGE);
        Glide.with(this)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.event_item_placeholder)
                .into(new PaletteGeneratingTargetWrapper(target, view, url));
    }

    @Override
    protected final void onFilterResults(List<DetailedEvent> items) {
        super.onFilterResults(items);
        /* Remove items that are not movies (e.g. theatre productions). Might add support for showing those later but right now this app is
        about movies only. */
        for (int i = items.size() - 1; i > 0; i--) {
            if (!items.get(i).getEventType().equals(Event.TYPE_MOVIE)) {
                items.remove(i);
            }
        }
    }

    protected abstract void onNewView(View view);

    protected abstract void onViewBound(View view, DetailedEvent event);

    @Override
    public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((EventCallbacks) getActivity()).onEventSelected(getItem(position));
    }
}
