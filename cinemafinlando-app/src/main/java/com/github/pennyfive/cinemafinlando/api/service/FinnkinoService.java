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
import com.github.pennyfive.cinemafinlando.api.model.Schedule;
import com.github.pennyfive.cinemafinlando.api.model.TheatreAreaContainer;

import java.io.IOException;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 *
 */
public interface FinnkinoService {

    @GET("/events/?includeGallery=true")
    public DetailedEventContainer getEvents(
            @Query("eventId") String eventId,
            @Query("listType") String listType,
            @Query("area") String area) throws IOException;

    @GET("/theatreAreas")
    public TheatreAreaContainer getTheatreAreas() throws IOException;

    @GET("/Schedule")
    public Schedule getSchedule(
            @Query("area") String theatreAreaId,
            @Query("dt") String date) throws IOException;
}
