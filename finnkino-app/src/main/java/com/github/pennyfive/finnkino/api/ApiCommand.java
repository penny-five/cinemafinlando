package com.github.pennyfive.finnkino.api;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class ApiCommand<T> {

    public final Map<String, String> getQueryParams() {
        Map<String, String> params = new HashMap<>();
        onSetQueryParams(params);
        return params;
    }

    protected void onSetQueryParams(Map<String, String> params) {

    }

    abstract String getPath();

    // TODO might be able to get right of this method using TypeToken
    abstract Class<T> getTypeClass();
}
