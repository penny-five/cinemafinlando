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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.github.pennyfive.finnkino.FinnkinoApplication.InjectUtils;
import com.github.pennyfive.finnkino.FinnkinoIntents;
import com.github.pennyfive.finnkino.R;
import com.github.pennyfive.finnkino.api.model.Event;
import com.github.pennyfive.finnkino.api.model.Gallery;
import com.github.pennyfive.finnkino.api.model.GalleryImage;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 *
 */
public class EventGalleryFragment extends Fragment {
    @InjectView(R.id.grid) GridView gridView;
    @Inject Picasso picasso;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InjectUtils.injectAll(this, view);

        Event event = getArguments().getParcelable(FinnkinoIntents.EXTRA_EVENT);
        gridView.setAdapter(new GalleryImageAdapter(event.getGallery()));
    }

    private class GalleryImageAdapter extends BinderAdapter<GalleryImage> {

        private GalleryImageAdapter(Gallery gallery) {
            super(getActivity(), gallery.getImages());
        }

        @Override
        public View newView(Context context, LayoutInflater inflater, int position) {
            return inflater.inflate(R.layout.item_gallery, null);
        }

        @Override
        public void bindView(Context context, View view, GalleryImage item) {
            picasso.load(item.getThumbnailUrl()).into((ImageView) view.findViewById(R.id.image));
        }
    }
}
