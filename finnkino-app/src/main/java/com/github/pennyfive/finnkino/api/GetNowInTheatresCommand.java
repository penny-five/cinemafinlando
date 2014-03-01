package com.github.pennyfive.finnkino.api;

import com.github.pennyfive.finnkino.api.model.Events;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class GetNowInTheatresCommand extends ApiCommand<Events> {

    @Override String getPath() {
        return "Events";
    }

    @Override public Map<String, String> getQueryParams() {
        Map<String, String> params = new HashMap<>();
        params.put("listType", "NowInTheatres");
        return params;
    }

    @Override Class<Events> getTypeClass() {
        return Events.class;
    }
}
