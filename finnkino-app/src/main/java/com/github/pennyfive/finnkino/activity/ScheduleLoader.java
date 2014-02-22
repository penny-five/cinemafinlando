package com.github.pennyfive.finnkino.activity;

import android.content.Context;

import com.github.pennyfive.finnkino.api.FinnkinoApi;
import com.github.pennyfive.finnkino.api.model.Schedule;
import com.github.pennyfive.finnkino.util.ApiQueryLoader;

import java.io.IOException;

/**
 *
 */
public class ScheduleLoader extends ApiQueryLoader<Schedule> {

    public ScheduleLoader(Context context) {
        super(context);
    }

    @Override
    public Schedule loadInBackground(FinnkinoApi api) throws IOException {
        return api.getScheduleForAll();
    }
}
