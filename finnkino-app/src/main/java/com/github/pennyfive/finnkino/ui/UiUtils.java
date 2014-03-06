package com.github.pennyfive.finnkino.ui;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 *
 */
public class UiUtils {

    public static int pixelsFromDip(Context context, int dip) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, metrics);
    }

    public static int pixelsFromResource(Context context, int resid) {
        return context.getResources().getDimensionPixelSize(resid);
    }

    private UiUtils() {}
}
