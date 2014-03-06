package com.github.pennyfive.finnkino.api;

import com.github.pennyfive.finnkino.api.model.TheatreAreas;

/**
 *
 */
public class GetTheatreAreasCommand extends ApiCommand<TheatreAreas> {

    @Override
    String getPath() {
        return "TheatreAreas";
    }

    @Override
    Class<TheatreAreas> getTypeClass() {
        return TheatreAreas.class;
    }
}
