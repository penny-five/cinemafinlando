package com.github.pennyfive.finnkino.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pennyfive.finnkino.FinnkinoApplication.InjectUtils;
import com.github.pennyfive.finnkino.R;
import com.github.pennyfive.finnkino.api.ApiCommand;
import com.github.pennyfive.finnkino.api.GetNowInTheatresCommand;
import com.github.pennyfive.finnkino.api.model.Event;
import com.github.pennyfive.finnkino.api.model.Events;
import com.github.pennyfive.finnkino.api.model.Show;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 *
 */
public class EventListFragment extends QueryListFragment<Event, Events> {
    @Inject Picasso picasso;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);
    }

    @Override
    protected ApiCommand<Events> onCreateCommand() {
        return new GetNowInTheatresCommand();
    }

    @Override
    protected View newView(Context context, LayoutInflater inflater) {
        return inflater.inflate(R.layout.item_show, null);
    }

    @Override
    protected void bindView(Context context, View view, Event event) {
        ((TextView) view.findViewById(R.id.text)).setText(event.getTitle());
        picasso.load(event.getImageUrl(Show.SIZE_LANDSCAPE_LARGE)).into((ImageView) view.findViewById(R.id.image));
    }
}
