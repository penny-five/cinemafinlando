/*
 * Copyright 2014 Joonas Lehtonen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pennyfive.finnkino.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.pennyfive.finnkino.api.model.TheatreArea;
import com.github.pennyfive.finnkino.api.model.TheatreAreas;
import com.github.pennyfive.finnkino.api.service.Command;
import com.github.pennyfive.finnkino.api.service.GetTheatreAreasCommand;

/**
 *
 */
public class TheatreAreaFragment extends QueryListFragment<TheatreArea, TheatreAreas> {

    public interface Callbacks {
        void onTheatreAreaSelected(TheatreArea area);
    }

    @Override
    protected Command<TheatreAreas> onCreateCommand() {
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
