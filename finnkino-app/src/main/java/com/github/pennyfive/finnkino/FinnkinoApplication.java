package com.github.pennyfive.finnkino;

import android.app.Activity;
import android.app.Application;
import android.view.View;

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

        public static void injectMembers(Object o) {
            instance.inject(o);
        }

        public static void injectViews(Object o, View view) {
            ButterKnife.inject(o, view);
        }

        public static void injectAll(Object o, View view) {
            injectMembers(o);
            injectViews(o, view);
        }

        public static void injectAll(Activity activity) {
            instance.inject(activity);
            ButterKnife.inject(activity);
        }
    }
}
