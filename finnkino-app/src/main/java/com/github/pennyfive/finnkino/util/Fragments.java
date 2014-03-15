package com.github.pennyfive.finnkino.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 *
 */
public class Fragments {

    public static <T extends Fragment> T instantiateWithArgs(Class<T> clazz, Bundle args) {
        try {
            T instance = clazz.newInstance();
            instance.setArguments(args);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T extends Fragment> T instantiateWithIntent(Class<T> clazz, Intent intent) {
        return instantiateWithArgs(clazz, intentToArgs(intent));
    }

    private static Bundle intentToArgs(Intent intent) {
        Bundle args = new Bundle();
        args.putAll(intent.getExtras());
        return args;
    }

    private Fragments() {}
}
