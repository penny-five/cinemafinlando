package com.github.pennyfive.finnkino.util;

import android.app.Fragment;
import android.os.Bundle;

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

    private Fragments() {}
}
