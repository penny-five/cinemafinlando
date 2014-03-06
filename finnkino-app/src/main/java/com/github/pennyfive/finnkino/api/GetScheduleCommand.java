package com.github.pennyfive.finnkino.api;

import com.github.pennyfive.finnkino.api.model.Schedule;

/**
 *
 */
public class GetScheduleCommand extends ApiCommand<Schedule> {

    @Override
    String getPath() {
        return "Schedule";
    }

    @Override
    Class<Schedule> getTypeClass() {
        return Schedule.class;
    }
}
