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
 * {@link Transformation} that adds blurry area to the bottom of the source bitmap. Used for {@link
 * com.github.pennyfive.cinemafinlando.ui.EventListActivity} item backgrounds.
 */
public class EventBackgroundBlurTransformation implements Transformation {
    private final RenderScript rs;
    private final View targetItem;

    public EventBackgroundBlurTransformation(RenderScript rs, View targetItem) {
        this.rs = rs;
        this.targetItem = targetItem;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        /* Allocate new bitmap for the result */
        Bitmap resultBitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), source.getConfig());
        Canvas canvas = new Canvas(resultBitmap);
        /* First draw the entire source bitmap... */
        canvas.drawBitmap(source, 0, 0, null);

        Bitmap blurredBottom = createBlurredBottomFrameBackground(source, measureBlurredAreaHeight(source));
        /* ..and then the blurred bottom area on top of that. */
        canvas.drawBitmap(
                blurredBottom,
                new Rect(0, 0, blurredBottom.getWidth(), blurredBottom.getHeight()),
                new Rect(0, resultBitmap.getHeight() - blurredBottom.getHeight(), resultBitmap.getWidth(), resultBitmap.getHeight()),
                null);
        /* recycle bitmaps that aren't needed anymore */
        blurredBottom.recycle();
        source.recycle();
        return resultBitmap;
    }

    private int measureBlurredAreaHeight(Bitmap source) {
        View bottomFrame = targetItem.findViewById(R.id.bottom_frame);
        float blurPortion = bottomFrame.getMeasuredHeight() / (float) targetItem.getMeasuredHeight();
        return (int) (source.getHeight() * blurPortion);
    }

    private Bitmap createBlurredBottomFrameBackground(Bitmap source, int frameHeight) {
        Bitmap subset = Bitmap.createBitmap(source, 0, source.getHeight() - frameHeight, source.getWidth(), frameHeight);
        Bitmap out = Bitmap.createBitmap(subset.getWidth(), subset.getHeight(), subset.getConfig());
        Allocation inAlloc = Allocation.createFromBitmap(rs, subset);
        Allocation outAlloc = Allocation.createFromBitmap(rs, out);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        blur.setRadius(5);
        blur.setInput(inAlloc);
        blur.forEach(outAlloc);
        outAlloc.copyTo(out);
        subset.recycle();
        return out;
    }

    @Override
    public String key() {
        return String.valueOf(rs.getApplicationContext().getResources().getConfiguration().orientation);
    }
}
