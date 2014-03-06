package com.github.pennyfive.finnkino.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.pennyfive.finnkino.api.ApiCommand;
import com.github.pennyfive.finnkino.api.GetTheatreAreasCommand;
import com.github.pennyfive.finnkino.api.model.TheatreArea;
import com.github.pennyfive.finnkino.api.model.TheatreAreas;

/**
 *
 */
public class TheatreAreaFragment extends QueryListFragment<TheatreArea, TheatreAreas> {

    public interface Callbacks {
        void onTheatreAreaSelected(TheatreArea area);
    }

    @Override
    protected ApiCommand<TheatreAreas> onCreateCommand() {
        return new GetTheatreAreasCommand();
    }

    @Override
    protected View newView(Context context, LayoutInflater inflater) {
        return inflater.inflate(android.R.layout.simple_list_item_1, null);
    }

    @Override
    protected void bindView(Context context, View view, TheatreArea item) {
        ((TextView) view.findViewById(android.R.id.text1)).setText(item.getName());
    }

    @Override
    protected void onItemClick(int position, View view, TheatreArea area) {
        ((Callbacks) getActivity()).onTheatreAreaSelected(area);
    }
}
