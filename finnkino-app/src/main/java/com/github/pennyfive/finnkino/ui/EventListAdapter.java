package com.github.pennyfive.finnkino.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pennyfive.finnkino.FinnkinoApplication.InjectUtils;
import com.github.pennyfive.finnkino.R;
import com.github.pennyfive.finnkino.api.model.Event;
import com.github.pennyfive.finnkino.api.model.Show;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

/**
 *
 */
public class EventListAdapter extends ArrayAdapter<Event> {
    @Inject Picasso picasso;

    public EventListAdapter(Context context, List<Event> events) {
        super(context, 0, events);
        InjectUtils.inject(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_show, null);
        }
        Event event = getItem(position);
        ((TextView) convertView.findViewById(R.id.text)).setText(event.getTitle());
        picasso.load(event.getImageUrl(Show.SIZE_LANDSCAPE_LARGE)).into((ImageView) convertView.findViewById(R.id.image));
        return convertView;
    }
}
