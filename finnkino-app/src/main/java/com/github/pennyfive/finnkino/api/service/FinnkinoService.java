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
import com.github.pennyfive.finnkino.api.model.TheatreAreas;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 *
 */
public interface FinnkinoService {

    @GET("/events")
    public Events getEvents(@Query("listType") String listType, @Query("area") String area);

    @GET("/theatreAreas")
    public TheatreAreas getTheatreAreas();
}
