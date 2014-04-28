package com.github.pennyfive.cinemafinlando.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v8.renderscript.RenderScript;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.CinemaFinlandoApplication.InjectUtils;
import com.github.pennyfive.cinemafinlando.CinemaFinlandoIntents;
import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.Schedule;
import com.github.pennyfive.cinemafinlando.api.model.Show;
import com.github.pennyfive.cinemafinlando.api.model.TheatreArea;
import com.github.pennyfive.cinemafinlando.api.service.Command;
import com.github.pennyfive.cinemafinlando.api.service.GetScheduleCommand;
import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.inject.Inject;

/**
 * Shows schedule for a Theatre area.
 */
public class TheatreAreaScheduleFragment extends QueryAbsListFragment<Show, Schedule> {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm");

    @Inject Picasso picasso;
    @Inject RenderScript rs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectMembers(this);
    }

    @Override
    protected Command<Schedule> onCreateCommand() {
        TheatreArea area = getArguments().getParcelable(CinemaFinlandoIntents.EXTRA_THEATRE_AREA);
        return GetScheduleCommand.forTheatreArea(area);
    }

    @Override
    protected AbsListView createAbsListView() {
        return (AbsListView) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_event_list, null);
    }

    @Override
    protected View newView(Context context, LayoutInflater inflater, Show item) {
        return inflater.inflate(R.layout.item_show, null);
    }

    @Override
    protected void bindView(Context context, View view, Show show) {
        ((TextView) view.findViewById(R.id.title)).setText(show.getTitle());
        ((TextView) view.findViewById(R.id.starting_time)).setText(show.getStartingTime().toString(TIME_FORMATTER));
        ((TextView) view.findViewById(R.id.ending_time)).setText(show.getEndingTime().toString(TIME_FORMATTER));
        ((TextView) view.findViewById(R.id.details)).setText(show.getTheatre() + ", " + show.getAuditorium());
    }
}
