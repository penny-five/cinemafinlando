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
import android.os.Bundle;
import android.support.v8.renderscript.RenderScript;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.CinemaFinlandoApplication.InjectUtils;
import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.Base;
import com.github.pennyfive.cinemafinlando.api.model.Container;
import com.github.pennyfive.cinemafinlando.api.model.Images;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import javax.inject.Inject;

/**
 *
 */
public abstract class BaseListFragment<T extends Base, E extends Container<T>> extends QueryAbsListFragment<T, E> {

    public interface Callbacks {
        void onBaseSelected(Base base);
    }

    @Inject Picasso picasso;
    @Inject RenderScript rs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectMembers(this);
    }

    @Override
    protected final AbsListView createAbsListView() {
        return (AbsListView) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_event_list, null);
    }

    @Override
    protected final View newView(Context context, LayoutInflater inflater, T base) {
        View view = inflater.inflate(R.layout.item_event, null);
        onNewView(view);
        return view;
    }

    @Override
    protected final void bindView(Context context, final View view, T t) {
        ((TextView) view.findViewById(R.id.text)).setText(t.getTitle());
        Transformation transform = new EventBackgroundBlurTransformation(rs, view);
        ImageView target = (ImageView) view.findViewById(R.id.image);
        String url = t.getImages().getUrl(Images.SIZE_LANDSCAPE_LARGE);
        picasso.load(url).placeholder(R.drawable.event_placeholder).transform(transform).into(target);
        onViewBound(view, t);
    }

    protected abstract void onNewView(View view);

    protected abstract void onViewBound(View view, T item);

    @Override
    public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((Callbacks) getActivity()).onBaseSelected(getItem(position));
    }
}
