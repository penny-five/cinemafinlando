package com.github.pennyfive.finnkino.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pennyfive.finnkino.FinnkinoApplication.InjectUtils;
import com.github.pennyfive.finnkino.FinnkinoIntents;
import com.github.pennyfive.finnkino.R;
import com.github.pennyfive.finnkino.api.model.Event;
import com.github.pennyfive.finnkino.util.Fragments;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 *
 */
public class EventActivity extends FragmentActivity {
    @Inject Picasso picasso;
    @InjectView(R.id.poster) ImageView posterImageView;
    @InjectView(R.id.image) ImageView eventImageView;
    @InjectView(R.id.name) TextView nameTextView;
    @InjectView(R.id.genres) TextView genreTextView;
    @InjectView(R.id.length) TextView durationTextView;
    @InjectView(R.id.tabs) SimplePagerTabWidget tabs;
    @InjectView(R.id.pager) ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        InjectUtils.injectAll(this);

        Event event = getIntent().getParcelableExtra(FinnkinoIntents.EXTRA_EVENT);

        getActionBar().setTitle(event.getTitle());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);

        picasso.load(event.getImageUrl(Event.SIZE_LANDSCAPE_LARGE)).into(eventImageView);
        picasso.load(event.getImageUrl(Event.SIZE_PORTRAIT_LARGE)).into(posterImageView);

        nameTextView.setText(event.getOriginalTitle());
        genreTextView.setText(event.getGenres());
        durationTextView.setText(getString(R.string.minutes, event.getLengthInMinutes()));

        pager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            private static final int POS_SYNOPSIS = 0;
            private static final int POS_SHOW_TIMES = 1;
            private static final int POS_INFORMATION = 2;

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case POS_SYNOPSIS:
                        return Fragments.instantiateWithIntent(EventSynopsisFragment.class, getIntent());
                    case POS_SHOW_TIMES:
                        return new Fragment();
                    case POS_INFORMATION:
                        return new Fragment();
                }
                return new Fragment();
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case POS_SYNOPSIS:
                        return getString(R.string.tab_title_synopsis);
                    case POS_SHOW_TIMES:
                        return getString(R.string.tab_title_show_times);
                    case POS_INFORMATION:
                        return getString(R.string.tab_title_information);
                    default:
                        throw new IllegalStateException();
                }
            }
        });

        tabs.setPager(pager);
    }
}
