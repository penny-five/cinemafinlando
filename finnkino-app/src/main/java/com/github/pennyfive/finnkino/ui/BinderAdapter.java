package com.github.pennyfive.finnkino.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * ArrayAdapter implementation that has similar interface as {@link android.widget.CursorAdapter}.
 */
public abstract class BinderAdapter<T> extends ArrayAdapter<T> {
    private final LayoutInflater inflater;

    public BinderAdapter(Context context, List<T> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = newView(getContext(), inflater);
        }
        bindView(getContext(), convertView, getItem(position));
        return convertView;
    }

    public abstract View newView(Context context, LayoutInflater inflater);

    public abstract void bindView(Context context, View view, T item);
}
