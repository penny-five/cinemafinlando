package com.github.pennyfive.finnkino;

import com.github.pennyfive.finnkino.activity.ScheduleLoader;
import com.github.pennyfive.finnkino.api.FinnkinoApi;
import com.github.pennyfive.finnkino.io.HttpClient;
import com.github.pennyfive.finnkino.util.Serializers;

import org.simpleframework.xml.Serializer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module(
        complete = true,
        injects = {
                FinnkinoApplication.class,
                FinnkinoApi.class,
                ScheduleLoader.class
        }
)
public class FinnkinoModule {

    @Provides @Singleton HttpClient provideHttpClient() {
        return new HttpClient();
    }

    @Provides @Singleton Serializer provideSerializer() {
        return Serializers.DEFAULT;
    }

    @Provides @Singleton FinnkinoApi provideFinnkinoApi() {
        return new FinnkinoApi();
    }
}
