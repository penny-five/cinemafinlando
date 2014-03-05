package com.github.pennyfive.finnkino.ui;

import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.pennyfive.finnkino.FinnkinoApplication.InjectUtils;
import com.github.pennyfive.finnkino.R;
import com.github.pennyfive.finnkino.api.ApiCommand;
import com.github.pennyfive.finnkino.api.model.Container;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * @param <T>
 * @param <S>
 */
public abstract class QueryListFragment<T, S extends Container<T>> extends Fragment implements LoaderCallbacks<S> {

    private class Adapter extends BinderAdapter<T> {

        public Adapter(Context context, List<T> items) {
            super(context, items);
        }

        @Override
        public View newView(Context context, LayoutInflater inflater) {
            return QueryListFragment.this.newView(context, inflater);
        }

        @Override
        public void bindView(Context context, View view, T item) {
            QueryListFragment.this.bindView(context, view, item);
        }
    }

    @InjectView(R.id.list) ListView listView;
    private Adapter adapter;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_query_list, container, false);
    }

    @Override
    public final void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InjectUtils.inject(this, view);
        getLoaderManager().initLoader(0, savedInstanceState, this);
    }

    @Override
    public final Loader<S> onCreateLoader(int id, Bundle args) {
        return new ApiQueryLoader<>(getActivity(), onCreateCommand());
    }

    protected abstract ApiCommand<S> onCreateCommand();

    @Override
    public final void onLoadFinished(Loader<S> loader, S data) {
        adapter = new Adapter(getActivity(), data.getItems());
        listView.setAdapter(adapter);
    }

    @Override
    public final void onLoaderReset(Loader<S> loader) {

    }

    @OnItemClick(R.id.list)
    public final void onItemClick(View view, int position) {
        if (position < listView.getHeaderViewsCount()) {
            QueryListFragment.this.onHeaderClick(position, view);
        } else {
            position = position - listView.getHeaderViewsCount();
            QueryListFragment.this.onItemClick(position, view, adapter.getItem(position));
        }
    }

    protected abstract View newView(Context context, LayoutInflater inflater);

    protected abstract void bindView(Context context, View view, T item);

    protected void onHeaderClick(int position, View view) {

    }

    protected void onItemClick(int position, View view, T item) {

    }
}
