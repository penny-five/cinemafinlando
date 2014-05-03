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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Extended ArrayAdapter implementation that has similar interface as {@link android.widget.CursorAdapter}.
 */
public abstract class BinderAdapter<T> extends ArrayAdapter<T> {
    private final LayoutInflater inflater;

    public BinderAdapter(Context context, List<T> items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
    }

    public BinderAdapter(Context context, T[] items) {
        super(context, 0, items);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = newView(inflater, parent);
        }
        bindView(convertView, getItem(position), position);
        return convertView;
    }

    protected View newView(LayoutInflater inflater, ViewGroup parent) {
        throw new IllegalStateException("Subclass didn't implement method");
    }

    protected void bindView(View view, T item, int position) {
        throw new IllegalStateException("Subclass didn't implement method");
    }

    @Override
    public final View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = newDropDownView(inflater, parent);
        }
        bindDropDownView(convertView, getItem(position), position);
        return convertView;
    }

    protected View newDropDownView(LayoutInflater inflater, ViewGroup parent) {
        throw new IllegalStateException("Subclass didn't implement method");
    }

    protected void bindDropDownView(View view, T item, int position) {
        throw new IllegalStateException("Subclass didn't implement method");
    }
}
