package com.github.pennyfive.cinemafinlando.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.pennyfive.cinemafinlando.CinemaFinlandoIntents;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEventGallery.Image;
import com.github.pennyfive.cinemafinlando.ui.UiUtils;
import com.github.pennyfive.cinemafinlando.ui.fragment.generic.MultiStateFragment;

public class GalleryItemFragment extends MultiStateFragment {
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        imageView = new ImageView(getActivity());
        Image image = getArguments().getParcelable(CinemaFinlandoIntents.EXTRA_IMAGE);
        Glide.with(this)
                .load(image.getUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.d("fuu", "EXCEPTION");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.d("fuu", "READY " + isFirstResource);
                        setState(STATE_CONTENT);
                        return false;
                    }
                })
                .into(imageView);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getStartupState() {
        return STATE_LOADING;
    }

    @Override
    protected View onCreateStateView(int state) {
        switch (state) {
            case STATE_CONTENT:
                return imageView;
            case STATE_LOADING:
                return UiUtils.inflateDefaultLoadingView(getActivity());
        }
        return null;
    }
}
