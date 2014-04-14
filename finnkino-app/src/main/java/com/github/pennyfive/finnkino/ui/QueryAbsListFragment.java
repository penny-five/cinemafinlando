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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.github.pennyfive.finnkino.R;
import com.github.pennyfive.finnkino.api.model.Container;
import com.github.pennyfive.finnkino.api.service.Command;

import java.util.List;

/**
 * Base class for Fragments that get their data from Finnkino REST service. Takes care of instantiating queries, handling IO errors etc.
 *
 * @param <T>
 * @param <S>
 */
public abstract class QueryAbsListFragment<T, S extends Container<T>> extends Fragment
        implements LoaderManager.LoaderCallbacks<S>, OnItemClickListener {

    private class Adapter extends BinderAdapter<T> {

        public Adapter(Context context, List<T> items) {
            super(context, items);
        }

        @Override
        public View newView(Context context, LayoutInflater inflater, int position) {
            return QueryAbsListFragment.this.newView(context, inflater, getItem(position));
        }

        @Override
        public void bindView(Context context, View view, T item) {
            QueryAbsListFragment.this.bindView(context, view, item);
        }

        @Override
        public int getViewTypeCount() {
            return QueryAbsListFragment.this.getViewTypeCount();
        }

        @Override
        public int getItemViewType(int position) {
            return QueryAbsListFragment.this.getItemViewType(position);
        }
    }

    private AbsListView absListView;
    private Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        absListView = createAbsListView();
        absListView.setId(R.id.list);
        absListView.setOnItemClickListener(this);
        return absListView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        absListView.setAdapter(adapter);
    }

    @Override
    public final void onLoaderReset(Loader<S> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    protected abstract AbsListView createAbsListView();

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
}
