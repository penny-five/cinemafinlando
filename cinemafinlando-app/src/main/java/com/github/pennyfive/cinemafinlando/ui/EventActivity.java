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

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.CinemaFinlandoApplication.InjectUtils;
import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.EventGallery;
import com.github.pennyfive.cinemafinlando.ui.CustomTypefaceTextView.CustomTypeface;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Shows information for a single {@link com.github.pennyfive.cinemafinlando.api.model.DetailedEvent}.
 */
public class EventActivity extends FragmentActivity {
    public static final String EXTRA_IMAGES = "images";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_ORIGINAL_TITLE = "original_title";
    public static final String EXTRA_GENRES = "genres";
    public static final String EXTRA_LENGTH = "length";

    @Inject Picasso picasso;
    @InjectView(R.id.poster) ImageView posterImageView;
    @InjectView(R.id.image) ImageView eventImageView;
    @InjectView(R.id.name) TextView nameTextView;
    @InjectView(R.id.genres) TextView genreTextView;
    @InjectView(R.id.length) TextView durationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        InjectUtils.injectAll(this);

        Bundle extras = getIntent().getExtras();

        getActionBar().setTitle(CustomTypeface.ROBOTO_LIGHT.wrap(this, extras.getString(EXTRA_TITLE)));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);

        EventGallery eventImageGallery = extras.getParcelable(EXTRA_IMAGES);
        picasso.load(eventImageGallery.getUrl(EventGallery.SIZE_LANDSCAPE_LARGE)).into(eventImageView);
        picasso.load(eventImageGallery.getUrl(EventGallery.SIZE_PORTRAIT_LARGE)).into(posterImageView);

        nameTextView.setText(extras.getString(EXTRA_ORIGINAL_TITLE));
        genreTextView.setText(extras.getString(EXTRA_GENRES));
        durationTextView.setText(getString(R.string.minutes, extras.getInt(EXTRA_LENGTH)));
    }
}
