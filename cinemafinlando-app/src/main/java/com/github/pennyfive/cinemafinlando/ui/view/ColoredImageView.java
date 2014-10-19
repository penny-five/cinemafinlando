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

package com.github.pennyfive.cinemafinlando.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.github.pennyfive.cinemafinlando.R;

/**
 *
 */
public class ColoredImageView extends ImageView {

    public ColoredImageView(Context context) {
        super(context);
    }

    public ColoredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttrs(attrs);
    }

    public ColoredImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(attrs);
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public ColoredImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        readAttrs(attrs);
    }

    private void readAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ColoredImageView);
        int color = ta.getColor(R.styleable.ColoredImageView_overlayColor, Color.TRANSPARENT);
        if (color != Color.TRANSPARENT) {
            setColor(color);
        }
    }

    public void setColor(int color) {
        PorterDuffColorFilter filter = new PorterDuffColorFilter(color, Mode.SRC_IN);
        setColorFilter(filter);
    }

    public void setColorResource(int colorResource) {
        setColor(getResources().getColor(colorResource));
    }
}
