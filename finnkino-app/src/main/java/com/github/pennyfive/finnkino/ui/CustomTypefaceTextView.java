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

package com.github.pennyfive.finnkino.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.github.pennyfive.finnkino.R;

/**
 * Custom {@link android.widget.TextView} that obtains typeface value from XML attributes and sets it automatically.
 */
public class CustomTypefaceTextView extends TextView {

    public enum CustomTypeface {
        FJALLA(0, "FjallaOne-Regular.ttf");

        private final int type;
        private final String filename;

        CustomTypeface(int type, String filename) {
            this.type = type;
            this.filename = filename;
        }
    }

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
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTypefaceTextView);
        int type = ta.getInt(R.styleable.CustomTypefaceTextView_customTypeface, -1);
        for (CustomTypeface customTypeface : CustomTypeface.values()) {
            if (customTypeface.type == type) {
                setCustomTypeface(customTypeface);
                break;
            }
        }
        ta.recycle();
    }

    public void setCustomTypeface(CustomTypeface typeface) {
        setTypeface(Typeface.createFromAsset(getResources().getAssets(), typeface.filename));
    }
}
