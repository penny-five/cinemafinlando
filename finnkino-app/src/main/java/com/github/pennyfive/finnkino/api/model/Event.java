package com.github.pennyfive.finnkino.api.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 */
@Root(strict = false)
public class Event {
    public static final String SIZE_PORTRAIT_MICRO = "EventMicroImagePortrait";
    public static final String SIZE_PORTRAIT_SMALL = "EventSmallImagePortrait";
    public static final String SIZE_PORTRAIT_LARGE = "EventLargeImagePortrait";
    public static final String SIZE_LANDSCAPE_SMALL = "EventSmallImageLandscape";
    public static final String SIZE_LANDSCAPE_LARGE = "EventLargeImageLandscape";

    @Element(name = "ID")
    private String id;
    @Element(name = "Title")
    private String title;
    @Element(name = "OriginalTitle")
    private String originalTitle;
    @Element(name = "ProductionYear")
    private int productionYear;
    @Element(name = "LengthInMinutes")
    private int lengthInMinutes;
    @Element(name = "ShortSynopsis")
    private String shortSynopsis;
    @Element(name = "Synopsis")
    private String synopsis;
    @Element(name = "Images")
    private Images images;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getShortSynopsis() {
        return shortSynopsis;
    }

    public String getImageUrl(String size) {
        return images.getUrl(size);
    }
}
