package com.github.pennyfive.cinemafinlando.api.service;

import com.github.pennyfive.cinemafinlando.api.model.Schedule;
import com.github.pennyfive.cinemafinlando.api.model.TheatreArea;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 *
 */
public class GetScheduleCommand implements Command<Schedule> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy");
    private final String language;
    private final String areaId;
    private final String date;

    private GetScheduleCommand(String language, String areaId, String date) {
        this.language = language;
        this.areaId = areaId;
        this.date = date;
    }

    @Override
    public Schedule execute(FinnkinoService service) throws IOException {
        return service.getSchedule(language, areaId, date);
    }

    public static GetScheduleCommand forTheatreArea(String language, TheatreArea area, LocalDate date) {
        return new GetScheduleCommand(language, area.getId(), date.toString(DATE_FORMATTER));
    }
}
