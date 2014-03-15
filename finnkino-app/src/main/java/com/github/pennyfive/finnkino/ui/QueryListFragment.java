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
import com.github.pennyfive.finnkino.api.ApiCommand;
import com.github.pennyfive.finnkino.api.model.Container;

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
        public View newView(Context context, LayoutInflater inflater) {
            return QueryListFragment.this.newView(context, inflater);
        }

        @Override
        public void bindView(Context context, View view, T item) {
            QueryListFragment.this.bindView(context, view, item);
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

    protected abstract ApiCommand<S> onCreateCommand();

    @Override
    public final void onLoadFinished(Loader<S> loader, S data) {
        // TODO error handling for cases when data is null
        adapter = new Adapter(getActivity(), data.getItems());
        listView.setAdapter(adapter);
    }

    @Override
    public final void onLoaderReset(Loader<S> loader) {

    }

    protected final T getItem(int position) {
        return adapter.getItem(position);
    }

    protected final ListView getListView() {
        return listView;
    }

    protected abstract View newView(Context context, LayoutInflater inflater);

    protected abstract void bindView(Context context, View view, T item);

    protected void onHeaderClick(int position, View view) {}

    protected void onItemClick(int position, View view, T item) {}
}
