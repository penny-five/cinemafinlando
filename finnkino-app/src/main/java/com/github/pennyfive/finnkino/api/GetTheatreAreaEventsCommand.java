package com.github.pennyfive.finnkino.api;

import com.github.pennyfive.finnkino.api.model.TheatreArea;

import java.util.Map;

/**
 *
 */
public class GetTheatreAreaEventsCommand extends GetEventsCommand {
    private final TheatreArea area;

    public GetTheatreAreaEventsCommand(TheatreArea area) {
        this.area = area;
    }

    @Override
    protected void onSetQueryParams(Map<String, String> params) {
        params.put(PARAM_LIST_TYPE, "NowInTheatres");
        params.put(PARAM_AREA, area.getId());
    }
}
