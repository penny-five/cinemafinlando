package com.github.pennyfive.cinemafinlando.api.service;

import com.github.pennyfive.cinemafinlando.api.model.Schedule;
import com.github.pennyfive.cinemafinlando.api.model.TheatreArea;

import java.io.IOException;

/**
 *
 */
public class GetScheduleCommand implements Command<Schedule> {
    private final String areaId;

    private GetScheduleCommand(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public Schedule execute(FinnkinoService service) throws IOException {
        return service.getSchedule(areaId);
    }

    public static GetScheduleCommand forTheatreArea(TheatreArea area) {
        return new GetScheduleCommand(area.getId());
    }
}
