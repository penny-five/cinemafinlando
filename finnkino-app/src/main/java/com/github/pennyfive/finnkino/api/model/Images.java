package com.github.pennyfive.finnkino.api.model;

import java.util.Map;

/**
 *
 */
public class Images {
    private final Map<String, String> sizeToUrl;

    public Images(Map<String, String> sizeToUrl) {
        this.sizeToUrl = sizeToUrl;
    }

    public String getUrl(String size) {
        return sizeToUrl.get(size);
    }
}
