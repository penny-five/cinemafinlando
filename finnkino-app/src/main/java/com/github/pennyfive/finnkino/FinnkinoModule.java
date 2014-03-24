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

package com.github.pennyfive.finnkino;

import android.content.Context;

import com.github.pennyfive.finnkino.api.service.FinnkinoService;
import com.github.pennyfive.finnkino.api.xml.Serializers;
import com.github.pennyfive.finnkino.ui.ApiQueryLoader;
import com.github.pennyfive.finnkino.ui.EventActivity;
import com.github.pennyfive.finnkino.ui.EventListActivity;
import com.github.pennyfive.finnkino.ui.EventListFragment;
import com.github.pennyfive.finnkino.ui.TheatreAreaFragment;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.converter.SimpleXMLConverter;

/**
 *
 */
@Module(
        complete = true,
        library = true,
        injects = {
                FinnkinoApplication.class,
                ApiQueryLoader.class,
                EventListActivity.class,
                EventListFragment.class,
                EventActivity.class,
                TheatreAreaFragment.class
        }
)
public class FinnkinoModule {
    private final Context context;

    FinnkinoModule(Context context) {
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
        return builder.build().create(FinnkinoService.class);
    }
}
