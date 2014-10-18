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

package com.github.pennyfive.cinemafinlando.ui.activity;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.pennyfive.cinemafinlando.CinemaFinlandoApplication.InjectUtils;
import com.github.pennyfive.cinemafinlando.CinemaFinlandoIntents;
import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEvent;
import com.github.pennyfive.cinemafinlando.api.model.EventGallery;
import com.github.pennyfive.cinemafinlando.ui.UiUtils;
import com.github.pennyfive.cinemafinlando.ui.activity.generic.ToolbarActivity;
import com.github.pennyfive.cinemafinlando.ui.fragment.EventDetailsFragment;
import com.github.pennyfive.cinemafinlando.ui.view.ListenableScrollView;
import com.github.pennyfive.cinemafinlando.ui.view.ListenableScrollView.OnScrollListener;

import butterknife.InjectView;

/**
 * <p>
 * Shows information for a single {@link DetailedEvent}.
 * </p>
 * <p>
 * Action bar fade effect is based on <a href="http://cyrilmottier.com/2013/05/24/pushing-the-actionbar-to-the-next-level/">this blog
 * post</a>.
 * </p>
 */
public class EventActivity extends ToolbarActivity implements OnScrollListener {
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.scrollview) ListenableScrollView scrollView;
    @InjectView(R.id.poster) ImageView posterImageView;
    @InjectView(R.id.image) ImageView eventImageView;
    @InjectView(R.id.name) TextView nameTextView;
    @InjectView(R.id.genres) TextView genreTextView;
    @InjectView(R.id.length) TextView durationTextView;

    private Drawable toolbarBackgroundDrawable;
    private Interpolator toolbarBackgroundAlphaInterpolator = new DecelerateInterpolator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        setContentView(R.layout.activity_event);

        setToolbarOverlay(true);

        InjectUtils.injectViews(this);

        if (savedInstanceState == null) {
            Fragment detailsFragment = UiUtils.instantiateWithIntent(EventDetailsFragment.class, getIntent());
            getSupportFragmentManager().beginTransaction().replace(R.id.container, detailsFragment).commit();
        }

        Bundle extras = getIntent().getExtras();

        getToolbar().setTitle(extras.getString(CinemaFinlandoIntents.EXTRA_TITLE));

        scrollView.setOnScrollListener(this);
        toolbarBackgroundDrawable = getResources().getDrawable(R.drawable.ab_solid_cinemafinlando);
        toolbarBackgroundDrawable.setAlpha(0);
        if (VERSION.SDK_INT <= VERSION_CODES.JELLY_BEAN_MR1) {
            toolbarBackgroundDrawable.setCallback(toolbarBackgroundCallback);
        }
        getToolbar().setBackgroundDrawable(toolbarBackgroundDrawable);

        EventGallery eventImageGallery = extras.getParcelable(CinemaFinlandoIntents.EXTRA_IMAGES);
        Glide.with(this)
                .load(eventImageGallery.getUrl(EventGallery.SIZE_LANDSCAPE_LARGE))
                .placeholder(R.drawable.event_promo_placeholder)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (!isFirstResource) {
                            eventImageView.setScaleType(ScaleType.CENTER_CROP);
                        }
                        return false;
                    }
                })
                .into(eventImageView);

        Glide.with(this)
                .load(eventImageGallery.getUrl(EventGallery.SIZE_PORTRAIT_LARGE))
                .placeholder(R.drawable.event_poster_placeholder)
                .into(posterImageView);

        nameTextView.setText(extras.getString(CinemaFinlandoIntents.EXTRA_ORIGINAL_TITLE));
        genreTextView.setText(extras.getString(CinemaFinlandoIntents.EXTRA_GENRES));

        int durationInMinutes = extras.getInt(CinemaFinlandoIntents.EXTRA_LENGTH);
        if (durationInMinutes > 0) {
            durationTextView.setText(getString(R.string.event_duration_minutes, durationInMinutes));
        } else {
            durationTextView.setText(R.string.event_duration_unknown_text);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getToolbar().setTitle(getIntent().getExtras().getString(CinemaFinlandoIntents.EXTRA_TITLE));
    }

    @Override
    public void onScroll(int position) {
        float ratio = Math.max(0, Math.min(1, position / (float) eventImageView.getHeight()));

        /* Adjust action bar background alpha when scrolled. */
        float interpolatedRatio = toolbarBackgroundAlphaInterpolator.getInterpolation(ratio);
        toolbarBackgroundDrawable.setAlpha((int) (interpolatedRatio * 255));

        /* Adjust event image translation when scrolled to create parallax effect. */
        eventImageView.setTranslationY(eventImageView.getHeight() * 0.4f * ratio);
    }

    /**
     * Devices older than JELLY_BEAN_MR2 require some extra work for the fading action bar to work.
     */
    private final Callback toolbarBackgroundCallback = new Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            getToolbar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {

        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
