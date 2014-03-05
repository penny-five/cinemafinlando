package com.github.pennyfive.finnkino.api;

import com.github.pennyfive.finnkino.api.model.Events;

import java.util.Map;

/**
 *
 */
public class GetNowInTheatresCommand extends ApiCommand<Events> {

    @Override String getPath() {
        return "Events";
    }

    @Override
    protected void onSetQueryParams(Map<String, String> params) {
        super.onSetQueryParams(params);
        params.put("listType", "NowInTheatres");
    }

    @Override Class<Events> getTypeClass() {
        return Events.class;
    }
}
