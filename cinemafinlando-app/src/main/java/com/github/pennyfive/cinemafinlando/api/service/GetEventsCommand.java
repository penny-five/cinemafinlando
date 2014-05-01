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

package com.github.pennyfive.cinemafinlando.api.service;

import com.github.pennyfive.cinemafinlando.api.model.DetailedEventContainer;
import com.github.pennyfive.cinemafinlando.api.model.TheatreArea;

import java.io.IOException;

/**
 *
 */
public class GetEventsCommand implements Command<DetailedEventContainer> {
    private static final String TYPE_COMING_SOON = "ComingSoon";
    private static final String TYPE_NOW_IN_THEATRES = "NowInTheatres";

    private final String eventId;
    private final String listType;
    private final String area;

    private GetEventsCommand(String eventId, String listType, String area) {
        this.eventId = eventId;
        this.listType = listType;
        this.area = area;
    }

    @Override
    public DetailedEventContainer execute(FinnkinoService service) throws IOException {
        return service.getEvents(eventId, listType, area);
    }

    public static GetEventsCommand comingSoon() {
        return new GetEventsCommand(null, TYPE_COMING_SOON, null);
    }

    public static GetEventsCommand nowInTheatres() {
        return new GetEventsCommand(null, TYPE_NOW_IN_THEATRES, null);
    }

    public static GetEventsCommand nowInTheatres(TheatreArea area) {
        return new GetEventsCommand(null, TYPE_NOW_IN_THEATRES, area.getId());
    }

    public static GetEventsCommand forEvent(String eventId) {
        return new GetEventsCommand(eventId, null, null);
    }
}
