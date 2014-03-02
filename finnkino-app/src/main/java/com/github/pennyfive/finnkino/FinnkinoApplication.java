package com.github.pennyfive.finnkino;

import android.app.Activity;
import android.app.Application;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

/**
 *
 */
public class FinnkinoApplication extends Application {
    private static FinnkinoApplication instance;

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        createObjectGraph();
    }

    private void createObjectGraph() {
        objectGraph = ObjectGraph.create(new FinnkinoModule(this));
    }

    private void inject(Object o) {
        objectGraph.inject(o);
    }

    public static class InjectUtils {

        public static void inject(Object o) {
            instance.inject(o);
        }

        public static void inject(Activity activity) {
            instance.inject(activity);
            ButterKnife.inject(activity);
        }
    }
}
