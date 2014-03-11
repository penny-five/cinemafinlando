package com.github.pennyfive.finnkino.api;

import java.util.Map;

/**
 *
 */
public class GetNowInTheatresCommand extends GetEventsCommand {

    @Override
    protected void onSetQueryParams(Map<String, String> params) {
        params.put(PARAM_LIST_TYPE, "NowInTheatres");
    }
}
