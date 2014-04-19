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

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;

import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.Container;
import com.github.pennyfive.cinemafinlando.api.service.Command;

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
    private ViewGroup loadingView;
    private Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        absListView = createAbsListView();
        absListView.setId(R.id.list);
        absListView.setOnItemClickListener(this);

        loadingView = (ViewGroup) inflater.inflate(R.layout.fragment_loading_layer, null);

        FrameLayout rootLayout = new FrameLayout(getActivity());
        rootLayout.addView(absListView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        rootLayout.addView(loadingView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        return rootLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(0, savedInstanceState, this);
        animateLoadingView();
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
        animateOutLoadingView();
    }

    private void animateLoadingView() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.loading_spinner_in);
        set.setTarget(loadingView.findViewById(R.id.spinner));
        set.start();
    }

    private void animateOutLoadingView() {
        /* Combination of two animations: spinner shrinks while the layout itself fades out */
        AnimatorSet set1 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.loading_layer_out);
        set1.setTarget(loadingView);
        AnimatorSet set2 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.loading_spinner_out);
        set2.setTarget(loadingView.findViewById(R.id.spinner));
        set1.playTogether(set2);
        set1.start();
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
