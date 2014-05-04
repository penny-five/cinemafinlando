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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.CinemaFinlandoApplication.InjectUtils;
import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEvent;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEventContainer;
import com.github.pennyfive.cinemafinlando.api.model.EventGallery;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 *
 */
public abstract class EventListFragment extends QueryAbsListFragment<DetailedEvent, DetailedEventContainer> {
    @Inject Picasso picasso;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectMembers(this);
    }

    @Override
    protected final AbsListView createAbsListView() {
        GridView grid = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.generic_gridview, null);
        grid.setNumColumns(getResources().getInteger(R.integer.event_list_columns));
        return grid;
    }

    @Override
    protected View newView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_event, parent, false);
        onNewView(view);
        return view;
    }

    @Override
    protected void bindView(View view, DetailedEvent event, int position) {
        ((TextView) view.findViewById(R.id.text)).setText(event.getTitle());
        ImageView target = (ImageView) view.findViewById(R.id.image);
        String url = event.getImages().getUrl(EventGallery.SIZE_LANDSCAPE_LARGE);
        picasso.load(url).placeholder(R.drawable.event_placeholder).into(target);
        onViewBound(view, event);
    }

    protected abstract void onNewView(View view);

    protected abstract void onViewBound(View view, DetailedEvent event);

    @Override
    public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((EventCallbacks) getActivity()).onEventSelected(getItem(position));
    }
}
