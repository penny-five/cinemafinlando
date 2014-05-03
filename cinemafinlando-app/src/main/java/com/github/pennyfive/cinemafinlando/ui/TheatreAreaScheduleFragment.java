package com.github.pennyfive.cinemafinlando.ui;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.CinemaFinlandoIntents;
import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.api.model.Schedule;
import com.github.pennyfive.cinemafinlando.api.model.Show;
import com.github.pennyfive.cinemafinlando.api.model.TheatreArea;
import com.github.pennyfive.cinemafinlando.api.service.Command;
import com.github.pennyfive.cinemafinlando.api.service.GetScheduleCommand;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Shows schedule for a Theatre area for the selected date. User can pick the date from a spinner in the action bar.
 */
public class TheatreAreaScheduleFragment extends QueryAbsListFragment<Show, Schedule> implements OnNavigationListener {
    private static final DateTimeFormatter SHOW_TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm");
    private static final DateTimeFormatter SPINNER_DAY_FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy");

    private TheatreArea theatreArea;
    private LocalDate date = LocalDate.now();
    private DateAdapter adapter;

    private class DateAdapter extends BinderAdapter<LocalDate> {
        private final String[] weekDays;

        public DateAdapter(Context context, LocalDate[] items) {
            super(context, items);
            weekDays = context.getResources().getStringArray(R.array.week_days);
        }

        @Override
        protected View newView(LayoutInflater inflater, ViewGroup parent) {
            return inflater.inflate(R.layout.schedule_ab_spinner, parent, false);
        }

        @Override
        protected void bindView(View view, LocalDate date, int position) {
            ((TextView) view.findViewById(R.id.title)).setText(theatreArea.getName());
            ((TextView) view.findViewById(R.id.subtitle)).setText(getDayString(position, date));
        }

        @Override
        protected View newDropDownView(LayoutInflater inflater, ViewGroup parent) {
            return inflater.inflate(R.layout.schedule_ab_dropdown, parent, false);
        }

        @Override
        protected void bindDropDownView(View view, LocalDate date, int position) {
            ((TextView) view.findViewById(R.id.title)).setText(getDayString(position, date));
            ((TextView) view.findViewById(R.id.subtitle)).setText(date.toString(SPINNER_DAY_FORMATTER));
        }

        private String getDayString(int position, LocalDate date) {
            switch (position) {
                case 0:
                    return getContext().getString(R.string.today);
                case 1:
                    return getContext().getString(R.string.tomorrow);
                default:
                    return weekDays[date.getDayOfWeek() - 1];
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DrawerActivity) getActivity()).setActionBarNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        theatreArea = getArguments().getParcelable(CinemaFinlandoIntents.EXTRA_THEATRE_AREA);

        adapter = new DateAdapter(getActivity(), getDaysForNextWeek());
        getActivity().getActionBar().setListNavigationCallbacks(adapter, this);
    }

    @Override
    protected Command<Schedule> onCreateCommand() {
        return GetScheduleCommand.forTheatreArea(theatreArea, date);
    }

    @Override
    protected AbsListView createAbsListView() {
        return (AbsListView) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_event_list, null);
    }

    @Override
    protected View newView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.item_show, parent, false);
    }

    @Override
    protected void bindView(View view, Show show, int position) {
        ((TextView) view.findViewById(R.id.title)).setText(show.getTitle());
        ((TextView) view.findViewById(R.id.starting_time)).setText(show.getStartingTime().toString(SHOW_TIME_FORMATTER));
        ((TextView) view.findViewById(R.id.ending_time)).setText(show.getEndingTime().toString(SHOW_TIME_FORMATTER));
        ((TextView) view.findViewById(R.id.details)).setText(show.getTheatre() + ", " + show.getAuditorium());
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        LocalDate selectedDate = adapter.getItem(itemPosition);
        if (!selectedDate.equals(date)) {
            date = selectedDate;
            restart();
        }
        return true;
    }

    private static LocalDate[] getDaysForNextWeek() {
        final LocalDate[] days = new LocalDate[6];
        days[0] = LocalDate.now();
        for (int i = 1; i < days.length; i++) {
            days[i] = days[i - 1].plusDays(1);
        }
        return days;
    }
}
