package com.github.pennyfive.cinemafinlando.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.CinemaFinlandoIntents;
import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEvent;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEventContainer;
import com.github.pennyfive.cinemafinlando.api.model.DetailedEventGallery.Image;
import com.github.pennyfive.cinemafinlando.api.service.GetEventsCommand;
import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import it.sephiroth.android.library.widget.HListView;

/**
 *
 */
public class EventDetailsFragment extends MultiStateFragment implements LoaderCallbacks<DetailedEventContainer> {
    private static final DateTimeFormatter RELEASE_DATE_FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy");

    @Override
    protected void onStateLayoutReady(Bundle savedInstanceState) {
        switchView(new View(getActivity()));
        getLoaderManager().initLoader(0, savedInstanceState, this);
    }

    @Override
    public Loader<DetailedEventContainer> onCreateLoader(int id, Bundle args) {
        String eventId = getArguments().getString(CinemaFinlandoIntents.EXTRA_EVENT_ID);
        return new ApiQueryLoader<>(getActivity(), GetEventsCommand.forEvent(eventId));
    }

    @Override
    public void onLoadFinished(Loader<DetailedEventContainer> loader, DetailedEventContainer data) {
        DetailedEvent event = data.getItems().get(0);
        switchView(createEventDetailsView(event));
    }

    private View createEventDetailsView(DetailedEvent event) {
        View view = View.inflate(getActivity(), R.layout.fragment_event_details, null);
        ((TextView) view.findViewById(R.id.synopsis)).setText(event.getSynopsis());
        ((TextView) view.findViewById(R.id.release_date)).setText(event.getReleaseDate().toString(RELEASE_DATE_FORMATTER));
        ((TextView) view.findViewById(R.id.age_rating)).setText(String.valueOf(event.getProductionYear()));

        HListView galleryView = (HListView) view.findViewById(R.id.gallery);
        galleryView.setAdapter(new BinderAdapter<Image>(getActivity(), event.getGallery().getImages()) {

            @Override
            public View newView(Context context, LayoutInflater inflater, int position) {
                return inflater.inflate(R.layout.item_event_gallery, null);
            }

            @Override
            public void bindView(Context context, View view, Image item) {
                ImageView target = (ImageView) view.findViewById(R.id.image);
                Picasso.with(getContext()).load(item.getThumbnailUrl()).placeholder(android.R.color.black).into(target);
            }
        });

        return view;
    }

    @Override
    public void onLoaderReset(Loader<DetailedEventContainer> loader) {

    }
}
