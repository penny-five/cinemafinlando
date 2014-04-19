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

package com.github.pennyfive.cinemafinlando.api.xml;

import com.github.pennyfive.cinemafinlando.api.model.TheatreArea;
import com.github.pennyfive.cinemafinlando.api.model.TheatreAreas;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.util.LinkedList;

/**
 *
 */
class TheatreAreasConverter implements Converter<TheatreAreas> {
    @Override
    public TheatreAreas read(InputNode node) throws Exception {
        LinkedList<TheatreArea> areas = new LinkedList<>();
        InputNode child;
        while ((child = node.getNext()) != null) {
            TheatreArea area = Serializers.DEFAULT.read(TheatreArea.class, child);
            if (area.isChildArea()) {
                areas.getLast().addChildArea(area);
            } else {
                areas.add(area);
            }
        }
        /* Remove the first area. It's a special item returned by the API that can be used to fetch events for all
         theatre areas but we don't want it. */
        areas.remove(0);
        return new TheatreAreas(areas);
    }

    @Override
    public void write(OutputNode node, TheatreAreas value) throws Exception {
        throw new UnsupportedOperationException();
    }
}
