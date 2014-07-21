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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.Container;
import com.github.pennyfive.cinemafinlando.api.service.Command;
import com.github.pennyfive.cinemafinlando.ui.ApiQueryLoader;
import com.github.pennyfive.cinemafinlando.ui.UiUtils;
import com.github.pennyfive.cinemafinlando.ui.adapter.BinderAdapter;

import java.util.Comparator;
import java.util.List;

/**
 * Base class for Fragments that get their data from Finnkino REST service. Takes care of instantiating queries, handling IO errors etc.
 *
 * @param <T>
 * @param <S>
 */
public abstract class QueryAbsListFragment<T, S extends Container<T>> extends MultiStateFragment
        implements LoaderManager.LoaderCallbacks<S>, OnItemClickListener {

    private class Adapter extends BinderAdapter<T> {

        public Adapter(Context context, List<T> items) {
            super(context, items);
        }

        @Override
        protected View newView(LayoutInflater inflater, ViewGroup parent) {
            return QueryAbsListFragment.this.newView(inflater, parent);
        }

        @Override
        protected void bindView(View view, T item, int position) {
            QueryAbsListFragment.this.bindView(view, item, position);
        }

        @Override
        public int getViewTypeCount() {
            return QueryAbsListFragment.this.getViewTypeCount();
        }

        @Override
        public boolean areAllItemsEnabled() {
            return itemsClickable;
        }

        @Override
        public boolean isEnabled(int position) {
            return itemsClickable;
        }

        @Override
        public int getItemViewType(int position) {
            return QueryAbsListFragment.this.getItemViewType(position);
        }
    }

    private Adapter adapter;
    private Comparator<T> comparator;
    private boolean itemsClickable = true;

    @Override
    protected int getStartupState() {
        return STATE_LOADING;
    }

    @Override
    protected View createStateView(int state) {
        switch (state) {
            case STATE_LOADING:
                return UiUtils.inflateDefaultLoadingView(getActivity());
            case STATE_CONTENT:
                return inflateContentListView(adapter);
            case STATE_ERROR:
                return UiUtils.inflateDefaultConnectionErrorView(getActivity(), new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setState(STATE_LOADING);
                    }
                });
            default:
                throw new IllegalStateException("state: " + state);
        }
    }

    @Override
    protected void onStateChanged(int state, View view) {
        super.onStateChanged(state, view);
        switch (state) {
            case STATE_LOADING:
                getLoaderManager().restartLoader(0, null, this);
                break;
            default:
                break;
        }
    }

    private View inflateContentListView(Adapter adapter) {
        AbsListView view = createAbsListView();
        view.setId(R.id.list);
        view.setOnItemClickListener(this);
        view.setAdapter(adapter);
        return view;
    }

    @Override
    public final Loader<S> onCreateLoader(int id, Bundle args) {
        return new ApiQueryLoader<>(getActivity(), onCreateCommand());
    }

    protected abstract Command<S> onCreateCommand();

    @Override
    public final void onLoadFinished(Loader<S> loader, S data) {
        if (data != null) {
            List<T> items = data.getItems();
            onFilterResults(items);
            adapter = new Adapter(getActivity(), items);
            if (comparator != null) {
                adapter.sort(comparator);
            }
            setState(STATE_CONTENT);
        } else {
            setState(STATE_ERROR);
        }
    }

    protected void onFilterResults(List<T> items) {

    }

    @Override
    public final void onLoaderReset(Loader<S> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * Call to restart loader and show loading view.
     * <p/>
     * {@link #onCreateCommand()} will be called after.
     */
    protected void restart() {
        setState(STATE_LOADING);
    }

    protected void setItemsClickable(boolean itemsClickable) {
        this.itemsClickable = itemsClickable;
    }

    protected abstract AbsListView createAbsListView();

    protected final T getItem(int position) {
        return adapter.getItem(position);
    }

    protected abstract View newView(LayoutInflater inflater, ViewGroup parent);

    protected abstract void bindView(View view, T item, int position);

    protected int getViewTypeCount() {
        return 1;
    }

    protected int getItemViewType(int position) {
        return 0;
    }

    protected final void sort(Comparator<T> comparator) {
        this.comparator = comparator;
        if (adapter != null) {
            adapter.sort(comparator);
            adapter.notifyDataSetChanged();
        }
    }
}
