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

import com.github.pennyfive.finnkino.api.model.Events;

import java.util.Map;

/**
 *
 */
public abstract class GetEventsCommand extends ApiCommand<Events> {
    protected final String PARAM_LIST_TYPE = "listType";
    protected final String PARAM_AREA = "area";
    protected final String PARAM_DATE = "dt";
    protected final String PARAM_EVENT_ID = "eventID";

    @Override
    final String getPath() {
        return "Events";
    }

    @Override
    abstract protected void onSetQueryParams(Map<String, String> params);

    @Override
    final Class<Events> getTypeClass() {
        return Events.class;
    }
}
