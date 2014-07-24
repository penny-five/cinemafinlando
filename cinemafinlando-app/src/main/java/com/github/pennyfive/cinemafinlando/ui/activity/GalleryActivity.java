package com.github.pennyfive.cinemafinlando.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.MenuItem;
import android.view.View;

import com.github.pennyfive.cinemafinlando.CinemaFinlandoIntents;
import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEventGallery;
import com.github.pennyfive.cinemafinlando.ui.UiUtils;
import com.github.pennyfive.cinemafinlando.ui.fragment.GalleryItemFragment;
import com.github.pennyfive.cinemafinlando.ui.view.CustomTypefaceTextView.CustomTypeface;

import butterknife.ButterKnife;

/**
 * Shows an image gallery associated to an event.
 */
public class GalleryActivity extends FragmentActivity {

    private static class GalleryAdapter extends FragmentPagerAdapter {
        private final DetailedEventGallery gallery;

        public GalleryAdapter(FragmentManager fm, DetailedEventGallery gallery) {
            super(fm);
            this.gallery = gallery;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putParcelable(CinemaFinlandoIntents.EXTRA_IMAGE, gallery.getImage(position));
            return UiUtils.instantiateWithArgs(GalleryItemFragment.class, args);
        }

        @Override
        public int getCount() {
            return gallery.getImageCount();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        getActionBar().setTitle(CustomTypeface.LIGHT.wrap(this, getIntent().getStringExtra(CinemaFinlandoIntents.EXTRA_TITLE)));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);

        DetailedEventGallery gallery = getIntent().getParcelableExtra(CinemaFinlandoIntents.EXTRA_GALLERY);
        GalleryAdapter adapter = new GalleryAdapter(getSupportFragmentManager(), gallery);

        ViewPager pager = ButterKnife.findById(this, R.id.pager);
        pager.setPageMargin(UiUtils.pixelsFromResource(this, R.dimen.gallery_activity_pager_margin));
        pager.setPageTransformer(true, new GalleryPageTransformer());
        pager.setAdapter(adapter);
        pager.setCurrentItem(getIntent().getIntExtra(CinemaFinlandoIntents.EXTRA_POSITION, 0), false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * Does depth transformation for pages
     * (see <a href="http://developer.android.com/training/animation/screen-slide.html#pagetransformer">here</a>)
     */
    private static class GalleryPageTransformer implements PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View page, float position) {
            if (position < -1) {
                page.setAlpha(0);
            } else if (position <= 0) {
                page.setAlpha(1);
                page.setScaleX(1);
                page.setScaleY(1);
            } else if (position <= 1) {
                page.setAlpha(1 - position);
                page.setTranslationX(page.getWidth() * -position);
                float scale = MIN_SCALE + ((1 - position) * (1 - MIN_SCALE));
                page.setScaleX(scale);
                page.setScaleY(scale);
            } else {
                page.setAlpha(0);
            }
        }
    }
}
