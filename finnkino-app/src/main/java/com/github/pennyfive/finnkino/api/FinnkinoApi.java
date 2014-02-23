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

package com.github.pennyfive.finnkino.api;

import com.github.pennyfive.finnkino.FinnkinoApplication.InjectUtils;
import com.github.pennyfive.finnkino.api.model.Schedule;
import com.github.pennyfive.finnkino.api.model.TheatreArea;
import com.github.pennyfive.finnkino.api.model.TheatreAreas;
import com.github.pennyfive.finnkino.util.HttpClient;

import org.simpleframework.xml.Serializer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 *
 */
public class FinnkinoApi {
    private static final String BASE_URL = "http://www.finnkino.fi/xml/";
    private static final String PATH_THEATRE_AREAS = "TheatreAreas";
    private static final String PATH_SCHEDULE = "Schedule";

    @Inject Serializer serializer;
    @Inject HttpClient http;

    @Inject public FinnkinoApi() {
        InjectUtils.inject(this);
    }

    private <T> T get(Class<T> clazz, String path, Map<String, String> queryParams) throws IOException {
        String response = http.get(BASE_URL + path);
        try {
            return serializer.read(clazz, response);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public List<TheatreArea> getTheatreAreas() throws IOException {
        return get(TheatreAreas.class, PATH_THEATRE_AREAS, Collections.EMPTY_MAP).getTheatreAreas();
    }

    public Schedule getScheduleForAll() throws IOException {
        return get(Schedule.class, PATH_SCHEDULE, Collections.EMPTY_MAP);
    }

}
