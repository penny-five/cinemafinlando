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

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.R;

/**
 * Custom {@link android.widget.TextView} that obtains typeface value from XML attributes and sets it automatically.
 */
public class CustomTypefaceTextView extends TextView {

    public CustomTypefaceTextView(Context context) {
        super(context);
    }

    public CustomTypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readCustomTypefaceFromAttributes(attrs);
    }

    public CustomTypefaceTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        readCustomTypefaceFromAttributes(attrs);
    }

    private void readCustomTypefaceFromAttributes(AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTypefaceTextView);
        int type = ta.getInt(R.styleable.CustomTypefaceTextView_customTypeface, CustomTypeface.REGULAR.type);

        for (CustomTypeface customTypeface : CustomTypeface.values()) {
            if (customTypeface.type == type) {
                int style = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "textStyle", Typeface.NORMAL);
                setCustomTypeface(customTypeface, style);
                break;
            }
        }
        ta.recycle();
    }

    public void setCustomTypeface(CustomTypeface typeface, int style) {
        if (!isInEditMode()) {
            setTypeface(typeface.create(getResources(), style));
        }
    }

    public enum CustomTypeface {
        LIGHT(0) {
            @Override
            protected Typeface create(Resources res, int style) {
                return Typeface.createFromAsset(res.getAssets(), "Roboto-Light.ttf");
            }
        },
        CONDENSED(1) {
            @Override
            protected Typeface create(Resources res, int style) {
                return Typeface.createFromAsset(res.getAssets(), "RobotoCondensed-Regular.ttf");
            }
        },
        REGULAR(2) {
            @Override
            protected Typeface create(Resources res, int style) {
                if (style == Typeface.BOLD) {
                    return Typeface.createFromAsset(res.getAssets(), "Roboto-Bold.ttf");
                }
                return Typeface.createFromAsset(res.getAssets(), "Roboto-Regular.ttf");
            }
        };

        private final int type;

        private CustomTypeface(int type) {
            this.type = type;
        }

        protected abstract Typeface create(Resources res, int style);

        public SpannableString wrap(Context context, int resid) {
            return wrap(context, context.getString(resid));
        }

        public SpannableString wrap(Context context, String src) {
            src = src != null ? src : "";
            SpannableString spannable = new SpannableString(src);
            spannable.setSpan(new CustomTypefaceSpan(context, this), 0, src.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            return spannable;
        }
    }

    private static class CustomTypefaceSpan extends MetricAffectingSpan {
        private final Typeface typeface;

        public CustomTypefaceSpan(Context context, CustomTypeface typeface) {
            this.typeface = typeface.create(context.getResources(), Typeface.NORMAL);
        }

        @Override
        public void updateMeasureState(TextPaint p) {
            p.setTypeface(typeface);
        }

        @Override
        public void updateDrawState(TextPaint tp) {
            tp.setTypeface(typeface);
        }
    }
}
