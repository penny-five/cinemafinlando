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

package com.github.pennyfive.cinemafinlando.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.R;

/**
 *
 */
public class UiUtils {

    private UiUtils() {}

    public static int pixelsFromDip(Context context, int dip) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, metrics);
    }

    public static int pixelsFromResource(Context context, int resid) {
        return context.getResources().getDimensionPixelSize(resid);
    }

    /**
     * Inflates a layout and sets given text resource string to TextView with id R.id.text. Obviously this requires that the provided layout file
     * contains a TextView with the id R.id.text.
     *
     * @param context
     * @param viewResid
     * @param textResid
     * @return
     */
    public static View inflateWithText(Context context, int viewResid, int textResid) {
        return inflateViewWithText(context, viewResid, context.getString(textResid));
    }

    /**
     * Inflates a layout and sets given text resource string to TextView with id R.id.text. Obviously this requires that the provided layout file
     * contains a TextView with the id R.id.text.
     *
     * @param context
     * @param viewResid
     * @param text
     * @return
     */
    public static View inflateViewWithText(Context context, int viewResid, String text) {
        View view = LayoutInflater.from(context).inflate(viewResid, null);
        ((TextView) view.findViewById(R.id.text)).setText(text);
        return view;
    }

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
}
