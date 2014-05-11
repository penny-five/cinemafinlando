package com.github.pennyfive.cinemafinlando.api.model;

import org.simpleframework.xml.Element;

/**
 *
 */
public class ContentDescriptor {
    @Element(name = "Name")
    String name;
    @Element(name = "ImageURL")
    String imageUrl;

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
