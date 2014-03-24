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

package com.github.pennyfive.finnkino.api.service;

import com.github.pennyfive.finnkino.api.model.Events;
import com.github.pennyfive.finnkino.api.model.TheatreArea;

/**
 *
 */
public class GetEventsCommand implements Command<Events> {
    private static final String TYPE_COMING_SOON = "ComingSoon";
    private static final String TYPE_NOW_IN_THEATRES = "NowInTheatres";

    private final String listType;
    private final String area;

    private GetEventsCommand(String listType) {
        this.listType = listType;
        this.area = null;
    }

    private GetEventsCommand(String listType, String area) {
        this.listType = listType;
        this.area = area;
    }

    @Override
    public Events execute(FinnkinoService service) {
        return service.getEvents(listType, area);
    }

    public static GetEventsCommand comingSoon() {
        return new GetEventsCommand(TYPE_COMING_SOON);
    }

    public static GetEventsCommand nowInTheatres() {
        return new GetEventsCommand(TYPE_NOW_IN_THEATRES);
    }

    public static GetEventsCommand nowInTheatres(TheatreArea area) {
        return new GetEventsCommand(TYPE_NOW_IN_THEATRES, area.getId());
    }
}
