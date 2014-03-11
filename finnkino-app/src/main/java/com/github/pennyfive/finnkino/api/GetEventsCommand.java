package com.github.pennyfive.finnkino.api;

import com.github.pennyfive.finnkino.api.model.Events;

import java.util.Map;

/**
 *
 */
public abstract class GetEventsCommand extends ApiCommand<Events> {
    protected final String PARAM_LIST_TYPE = "listType";
    protected final String PARAM_AREA = "area";
    protected final String PARAM_DATE = "dt";
    protected final String PARAM_EVENT_ID = "eventID";

    @Override
    final String getPath() {
        return "Events";
    }

    @Override
    abstract protected void onSetQueryParams(Map<String, String> params);

    @Override
    final Class<Events> getTypeClass() {
        return Events.class;
    }
}
