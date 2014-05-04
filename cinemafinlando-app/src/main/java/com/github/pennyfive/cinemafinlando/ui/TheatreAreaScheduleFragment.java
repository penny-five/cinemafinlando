package com.github.pennyfive.cinemafinlando.ui;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
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
public class TheatreAreaScheduleFragment extends QueryAbsListFragment<Show, Schedule> implements OnNavigationListener, OnClickListener {
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

        setItemsClickable(false);

        adapter = new DateAdapter(getActivity(), getDaysForNextWeek());
        getActivity().getActionBar().setListNavigationCallbacks(adapter, this);
    }

    @Override
    protected Command<Schedule> onCreateCommand() {
        return GetScheduleCommand.forTheatreArea(theatreArea, date);
    }

    @Override
    protected AbsListView createAbsListView() {
        GridView grid = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.generic_gridview, null);
        grid.setNumColumns(1); // Makes no sense to have more than 1 column in a list that shows a timetable
        return grid;
    }

    @Override
    protected View newView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_show, parent, false);
        view.findViewById(R.id.overflow).setOnClickListener(this);
        return view;
    }

    @Override
    protected void bindView(View view, Show show, int position) {
        view.findViewById(R.id.overflow).setTag(position);
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
        final LocalDate[] days = new LocalDate[7];
        days[0] = LocalDate.now();
        for (int i = 1; i < days.length; i++) {
            days[i] = days[i - 1].plusDays(1);
        }
        return days;
    }

    @Override
    public void onClick(View v) {
        showPopupMenu(v, (Integer) v.getTag());
    }

    private void showPopupMenu(View view, final int position) {
        PopupMenu menu = new PopupMenu(getActivity(), view);
        menu.inflate(R.menu.show);
        menu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Show show = getItem(position);
                switch (item.getItemId()) {
                    case R.id.menu_view_event:
                        viewEvent(show);
                        break;
                    case R.id.menu_add_to_calendar:
                        addToCalendar(show);
                        break;
                    case R.id.menu_reserve_tickets:
                        reserveTickets(show);
                        break;
                    default:
                        throw new IllegalStateException();
                }
                return true;
            }
        });
        menu.show();
    }

    private void viewEvent(Show show) {
        ((EventCallbacks) getActivity()).onEventSelected(show);
    }

    private void addToCalendar(Show show) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Events.CONTENT_URI);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, show.getStartingTime().getMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, show.getEndingTime().getMillis());
        intent.putExtra(Events.TITLE, show.getTitle());
        intent.putExtra(Events.EVENT_LOCATION, show.getTheatre() + ", " + show.getAuditorium());
        startActivity(intent);
    }

    private void reserveTickets(Show show) {
        Uri uri = Uri.parse("https://www.finnkino.fi/Websales/Show")
                .buildUpon()
                .appendPath(show.getShowId())
                .appendQueryParameter("area", theatreArea.getId())
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
