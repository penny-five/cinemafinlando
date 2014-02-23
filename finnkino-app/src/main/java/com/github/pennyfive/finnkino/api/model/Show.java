/*
 * Copyright 2014 Joonas Lehtonen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pennyfive.finnkino.api.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 */
@Root(strict = false)
public class Show {
    public static final String SIZE_PORTRAIT_MICRO = "EventMicroImagePortrait";
    public static final String SIZE_PORTRAIT_SMALL = "EventSmallImagePortrait";
    public static final String SIZE_PORTRAIT_LARGE = "EventLargeImagePortrait";
    public static final String SIZE_LANDSCAPE_SMALL = "EventSmallImageLandscape";
    public static final String SIZE_LANDSCAPE_LARGE = "EventLargeImageLandscape";

    @Element(name = "ID")
    private String id;
    @Element(name = "EventID")
    private String eventId;
    @Element(name = "Title")
    private String title;
    @Element(name = "OriginalTitle")
    private String originalTitle;
    @Element(name = "ProductionYear")
    private int productionYear;
    @Element(name = "LengthInMinutes")
    private int lengthInMinutes;
    @Element(name = "Images")
    private ImageUrlContainer imageUrlContainer;

    public String getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
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

    public String getImageUrl(String size) {
        return imageUrlContainer.getUrl(size);
    }
}
