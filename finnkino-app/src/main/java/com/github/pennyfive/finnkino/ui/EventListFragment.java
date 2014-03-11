package com.github.pennyfive.finnkino.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pennyfive.finnkino.FinnkinoApplication.InjectUtils;
import com.github.pennyfive.finnkino.FinnkinoIntents;
import com.github.pennyfive.finnkino.R;
import com.github.pennyfive.finnkino.api.ApiCommand;
import com.github.pennyfive.finnkino.api.GetComingSoonCommand;
import com.github.pennyfive.finnkino.api.GetNowInTheatresCommand;
import com.github.pennyfive.finnkino.api.GetTheatreAreaEventsCommand;
import com.github.pennyfive.finnkino.api.model.Event;
import com.github.pennyfive.finnkino.api.model.Events;
import com.github.pennyfive.finnkino.api.model.Show;
import com.github.pennyfive.finnkino.api.model.TheatreArea;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 *
 */
public class EventListFragment extends QueryListFragment<Event, Events> {
    public static final String EXTRA_LIST_TYPE = "list_type";
    public static final String LIST_TYPE_NOW_IN_THEATRES = "now_playing";
    public static final String LIST_TYPE_COMING_SOON = "coming_soong";
    public static final String LIST_TYPE_THEATRE_AREA = "theatre_area";

    public interface Callbacks {
        void onEventSelected(Event event);
    }

    @Inject Picasso picasso;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDividerHeight(UiUtils.pixelsFromResource(getActivity(), R.dimen.event_list_divider_height));
        getListView().setDrawSelectorOnTop(true);
    }

    @Override
    protected ApiCommand<Events> onCreateCommand() {
        Bundle args = getArguments();
        switch (args.getString(EXTRA_LIST_TYPE)) {
            case LIST_TYPE_COMING_SOON:
                return new GetComingSoonCommand();
            case LIST_TYPE_NOW_IN_THEATRES:
                return new GetNowInTheatresCommand();
            case LIST_TYPE_THEATRE_AREA:
                return new GetTheatreAreaEventsCommand((TheatreArea) args.get(FinnkinoIntents.EXTRA_THEATRE_AREA));
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    protected View newView(Context context, LayoutInflater inflater) {
        return inflater.inflate(R.layout.item_event, null);
    }

    @Override
    protected void bindView(Context context, View view, Event event) {
        ((TextView) view.findViewById(R.id.text)).setText(event.getTitle());
        picasso.load(event.getImageUrl(Show.SIZE_LANDSCAPE_LARGE)).into((ImageView) view.findViewById(R.id.image));
    }

    @Override
    protected void onItemClick(int position, View view, Event event) {
        ((Callbacks) getActivity()).onEventSelected(event);
    }
}
