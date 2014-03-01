package com.github.pennyfive.finnkino.api;

import java.util.Collections;
import java.util.Map;

/**
 *
 */
public abstract class ApiCommand<T> {

    public Map<String, String> getQueryParams() {
        return Collections.emptyMap();
    }

    abstract String getPath();

    abstract Class<T> getTypeClass();
}
