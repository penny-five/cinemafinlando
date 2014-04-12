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
        if (intent != null) {
            args.putAll(intent.getExtras());
        }
        return args;
    }

    private Fragments() {}
}
