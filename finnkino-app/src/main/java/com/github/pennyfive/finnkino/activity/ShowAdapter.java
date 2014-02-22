package com.github.pennyfive.finnkino.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.pennyfive.finnkino.R;
import com.github.pennyfive.finnkino.api.model.Show;

import java.util.List;

/**
 *
 */
class ShowAdapter extends ArrayAdapter<Show> {

    public ShowAdapter(Context context, List<Show> shows) {
        super(context, 0, shows);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_show, null);
        }
        Show show = getItem(position);
        ((TextView) convertView.findViewById(R.id.text)).setText(show.getTitle());
        return convertView;
    }
}
