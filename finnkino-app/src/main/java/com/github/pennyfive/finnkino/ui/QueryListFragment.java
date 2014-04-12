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
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.github.pennyfive.finnkino.R;
import com.github.pennyfive.finnkino.api.model.Container;
import com.github.pennyfive.finnkino.api.service.Command;

import java.util.List;

/**
 * @param <T>
 * @param <S>
 */
public abstract class QueryListFragment<T, S extends Container<T>> extends Fragment implements LoaderManager.LoaderCallbacks<S> {

    private class Adapter extends BinderAdapter<T> {

        public Adapter(Context context, List<T> items) {
            super(context, items);
        }

        @Override
        public View newView(Context context, LayoutInflater inflater, int position) {
            return QueryListFragment.this.newView(context, inflater, getItem(position));
        }

        @Override
        public void bindView(Context context, View view, T item) {
            QueryListFragment.this.bindView(context, view, item);
        }

        @Override
        public int getViewTypeCount() {
            return QueryListFragment.this.getViewTypeCount();
        }

        @Override
        public int getItemViewType(int position) {
            return QueryListFragment.this.getItemViewType(position);
        }
    }

    private ListView listView;
    private Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_query_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < listView.getHeaderViewsCount()) {
                    QueryListFragment.this.onHeaderClick(position, view);
                } else {
                    position = position - listView.getHeaderViewsCount();
                    QueryListFragment.this.onItemClick(position, view, adapter.getItem(position));
                }
            }
        });
        getLoaderManager().initLoader(0, savedInstanceState, this);
    }

    @Override
    public final Loader<S> onCreateLoader(int id, Bundle args) {
        return new ApiQueryLoader<>(getActivity(), onCreateCommand());
    }

    protected abstract Command<S> onCreateCommand();

    @Override
    public final void onLoadFinished(Loader<S> loader, S data) {
        // TODO error handling for cases when data is null
        adapter = new Adapter(getActivity(), data.getItems());
        listView.setAdapter(adapter);
    }

    @Override
    public final void onLoaderReset(Loader<S> loader) {

    }

    protected final void addHeaderView(View v) {
        listView.addHeaderView(v);
    }

    protected final void addHeaderView(View v, Object data, boolean isSelectable) {
        listView.addHeaderView(v, data, isSelectable);
    }

    protected final ListView getListView() {
        return listView;
    }

    protected final T getItem(int position) {
        return adapter.getItem(position);
    }

    protected abstract View newView(Context context, LayoutInflater inflater, T item);

    protected abstract void bindView(Context context, View view, T item);

    protected int getViewTypeCount() {
        return 1;
    }

    protected int getItemViewType(int position) {
        return 0;
    }

    protected void onHeaderClick(int position, View view) {}

    protected void onItemClick(int position, View view, T item) {}
}