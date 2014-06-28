package com.github.pennyfive.cinemafinlando.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

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
        pager.setPageMargin(UiUtils.pixelsFromResource(this, R.dimen.gallery_pager_margin));
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
}
