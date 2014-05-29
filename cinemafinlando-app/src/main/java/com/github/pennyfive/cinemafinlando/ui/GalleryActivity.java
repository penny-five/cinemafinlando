package com.github.pennyfive.cinemafinlando.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.github.pennyfive.cinemafinlando.CinemaFinlandoApplication.InjectUtils;
import com.github.pennyfive.cinemafinlando.CinemaFinlandoIntents;
import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEventGallery;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEventGallery.Image;
import com.github.pennyfive.cinemafinlando.ui.view.CustomTypefaceTextView.CustomTypeface;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 *
 */
public class GalleryActivity extends FragmentActivity {

    public static class GalleryFragment extends MultiStateFragment implements Callback {
        @Inject Picasso picasso;
        private ImageView imageView;

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            InjectUtils.injectMembers(this);

            imageView = new ImageView(getActivity());

            Image image = getArguments().getParcelable(CinemaFinlandoIntents.EXTRA_IMAGE);
            picasso.load(image.getUrl()).into(imageView, this);
        }

        @Override
        public void onDestroyView() {
            // cancel request or otherwise Picasso keeps a reference to set Callback
            picasso.cancelRequest(imageView);
            super.onDestroyView();
        }

        @Override
        protected void onStateLayoutReady(Bundle savedInstanceState) {
            switchView(UiUtils.inflateDefaultLoadingView(getActivity()));
        }

        @Override
        public void onSuccess() {
            switchView(imageView);
        }

        @Override
        public void onError() {

        }
    }

    private class GalleryAdapter extends FragmentPagerAdapter {
        private final DetailedEventGallery gallery;

        public GalleryAdapter(FragmentManager fm, DetailedEventGallery gallery) {
            super(fm);
            this.gallery = gallery;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putParcelable(CinemaFinlandoIntents.EXTRA_IMAGE, gallery.getImage(position));
            return UiUtils.instantiateWithArgs(GalleryFragment.class, args);
        }

        @Override
        public int getCount() {
            return gallery.getImageCount();
        }
    }

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        getActionBar().setTitle(CustomTypeface.ROBOTO_LIGHT.wrap(this, getIntent().getStringExtra(CinemaFinlandoIntents.EXTRA_TITLE)));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);

        DetailedEventGallery gallery = getIntent().getParcelableExtra(CinemaFinlandoIntents.EXTRA_GALLERY);
        GalleryAdapter adapter = new GalleryAdapter(getSupportFragmentManager(), gallery);

        pager = ButterKnife.findById(this, R.id.pager);
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
