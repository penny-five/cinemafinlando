package com.github.pennyfive.cinemafinlando.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.pennyfive.cinemafinlando.CinemaFinlandoApplication.InjectUtils;
import com.github.pennyfive.cinemafinlando.CinemaFinlandoIntents;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEventGallery.Image;
import com.github.pennyfive.cinemafinlando.ui.UiUtils;
import com.github.pennyfive.cinemafinlando.ui.fragment.generic.MultiStateFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class GalleryItemFragment extends MultiStateFragment implements Callback {
    @Inject Picasso picasso;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectMembers(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        imageView = new ImageView(getActivity());
        Image image = getArguments().getParcelable(CinemaFinlandoIntents.EXTRA_IMAGE);
        picasso.load(image.getUrl()).into(imageView, this);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        // cancel request or otherwise Picasso keeps a reference to set Callback
        picasso.cancelRequest(imageView);
        super.onDestroyView();
    }

    @Override
    protected int getStartupState() {
        return STATE_LOADING;
    }

    @Override
    protected View createStateView(int state) {
        switch (state) {
            case STATE_CONTENT:
                return imageView;
            case STATE_LOADING:
                return UiUtils.inflateDefaultLoadingView(getActivity());
        }
        return null;
    }

    @Override
    public void onSuccess() {
        setState(STATE_CONTENT);
    }

    @Override
    public void onError() {

    }
}
