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
 *
 */
@Root(strict = false)
public class Show extends Base {
    @Element(name = "dttmShowStart")
    private DateTime startingTime;
    @Element(name = "dttmShowEnd")
    private DateTime endingTime;
    @Element(name = "TheatreAndAuditorium")
    private String theatreAndAuditorium;

    public DateTime getEndingTime() {
        return endingTime;
    }

    public DateTime getStartingTime() {
        return startingTime;
    }

    public String getTheatre() {
        return theatreAndAuditorium.split(",")[0].trim();
    }

    public String getCity() {
        return theatreAndAuditorium.split(",")[1].trim();
    }

    public String getAuditorium() {
        return theatreAndAuditorium.split(",")[2].trim();
    }
}
