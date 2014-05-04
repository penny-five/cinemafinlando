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

package com.github.pennyfive.cinemafinlando.api.model;

import org.joda.time.DateTime;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Event is a base class containing shared data for {@link DetailedEvent}s and {@link Show}s.
 * <p/>
 */
@Root(strict = false)
public abstract class Event {
    @Element(name = "Title")
    private String title;
    @Element(name = "OriginalTitle")
    private String originalTitle;
    @Element(name = "ProductionYear")
    private int productionYear;
    @Element(name = "LengthInMinutes")
    private int lengthInMinutes;
    @Element(name = "dtLocalRelease")
    private DateTime releaseDate;
    @Element(name = "Genres")
    private String genres;
    @Element(name = "Images")
    private EventGallery images;

    public abstract String getEventId();

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

    public DateTime getReleaseDate() {
        return releaseDate;
    }

    public String getGenres() {
        return genres;
    }

    public EventGallery getImages() {
        return images;
    }
}
