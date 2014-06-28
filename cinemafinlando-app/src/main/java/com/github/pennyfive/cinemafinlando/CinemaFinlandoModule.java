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

package com.github.pennyfive.cinemafinlando;

import android.content.Context;

import com.github.pennyfive.cinemafinlando.api.service.FinnkinoService;
import com.github.pennyfive.cinemafinlando.api.xml.Serializers;
import com.github.pennyfive.cinemafinlando.ui.ApiQueryLoader;
import com.github.pennyfive.cinemafinlando.ui.activity.EventActivity;
import com.github.pennyfive.cinemafinlando.ui.activity.generic.EventListActivity;
import com.github.pennyfive.cinemafinlando.ui.fragment.ComingSoonListFragment;
import com.github.pennyfive.cinemafinlando.ui.fragment.EventDetailsFragment;
import com.github.pennyfive.cinemafinlando.ui.fragment.GalleryItemFragment;
import com.github.pennyfive.cinemafinlando.ui.fragment.NavigationFragment;
import com.github.pennyfive.cinemafinlando.ui.fragment.NowShowingListFragment;
import com.github.pennyfive.cinemafinlando.ui.fragment.TheatreAreaScheduleFragment;
import com.github.pennyfive.cinemafinlando.ui.fragment.generic.EventListFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.ErrorHandler;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.converter.SimpleXMLConverter;

/**
 *
 */
@Module(
        complete = true,
        injects = {
                CinemaFinlandoApplication.class,
                ApiQueryLoader.class,
                NavigationFragment.class,
                EventListActivity.class,
                EventListFragment.class,
                ComingSoonListFragment.class,
                NowShowingListFragment.class,
                TheatreAreaScheduleFragment.class,
                EventActivity.class,
                EventDetailsFragment.class,
                GalleryItemFragment.class
        }
)
public class CinemaFinlandoModule {
    private final Context context;

    CinemaFinlandoModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @Singleton
    Picasso providePicasso() {
        return Picasso.with(context);
    }

    @Provides
    @Singleton
    FinnkinoService provideFinnkinoService() {
        Builder builder = new Builder();
        builder.setEndpoint("http://www.finnkino.fi/xml/");
        builder.setConverter(new SimpleXMLConverter(Serializers.DEFAULT));
        builder.setLogLevel(LogLevel.BASIC);
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public Throwable handleError(RetrofitError cause) {
                return new IOException(cause);
            }
        });
        return builder.build().create(FinnkinoService.class);
    }
}
