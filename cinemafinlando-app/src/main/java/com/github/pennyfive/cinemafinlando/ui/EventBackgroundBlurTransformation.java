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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.view.View;

import com.github.pennyfive.cinemafinlando.R;
import com.squareup.picasso.Transformation;

/**
 * {@link Transformation} that adds blurry area to the bottom of the source bitmap. Used for event list item backgrounds.
 * <p/>
 * TODO need to optimize and add some comments
 */
public class EventBackgroundBlurTransformation implements Transformation {
    private final Context context;
    private final float blurPortion;

    public EventBackgroundBlurTransformation(View eventListItem) {
        this.context = eventListItem.getContext().getApplicationContext();
        View bottomFrame = eventListItem.findViewById(R.id.bottom_frame);
        this.blurPortion = bottomFrame.getMeasuredHeight() / (float) eventListItem.getMeasuredHeight();
    }

    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap out = Bitmap.createBitmap(source.getWidth(), source.getHeight(), source.getConfig());
        RenderScript rs = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(rs, source);
        Allocation output = Allocation.createFromBitmap(rs, out);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        blur.setRadius(5);
        blur.setInput(input);
        blur.forEach(output);
        output.copyTo(out);
        int blurHeight = (int) (out.getHeight() * blurPortion);
        Rect targetRect = new Rect(0, 0, source.getWidth(), source.getHeight() - blurHeight);
        Canvas canvas = new Canvas(out);
        canvas.drawBitmap(source, targetRect, targetRect, null);
        source.recycle();
        rs.destroy();
        return out;
    }

    @Override
    public String key() {
        return String.valueOf(context.getResources().getConfiguration().orientation);
    }
}
