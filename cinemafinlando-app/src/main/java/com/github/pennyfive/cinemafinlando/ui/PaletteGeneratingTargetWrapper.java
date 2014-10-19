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

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.Palette.PaletteAsyncListener;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class PaletteGeneratingTargetWrapper extends BitmapImageViewTarget {
    private final int TAG_KEY = 1231242142;
    private final String key;
    private final WeakReference<View> targetRef;
    private final Map<String, Palette> paletteCache = new ConcurrentHashMap<>();

    public PaletteGeneratingTargetWrapper(ImageView view, View target, String key) {
        super(view);
        targetRef = new WeakReference<>(target);
        this.key = key;
        target.setTag(TAG_KEY, key);
    }

    @Override
    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        super.onResourceReady(resource, glideAnimation);
        View target = targetRef.get();
        if (target != null && target.getTag(TAG_KEY).equals(key)) {
            Palette cached = paletteCache.get(key);
            if (cached == null) {
                Palette.generateAsync(resource, new PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        paletteCache.put(key, palette);
                        View target = targetRef.get();
                        if (target != null && target.getTag(TAG_KEY).equals(key)) {
                            animateBackgroundColor(target, palette);
                        }
                    }
                });
            } else {
                animateBackgroundColor(target, cached);
            }
        }
    }

    private void animateBackgroundColor(final View view, Palette palette) {
        int original;
        Drawable backgroundDrawable = view.getBackground();
        if (backgroundDrawable != null && backgroundDrawable instanceof ColorDrawable) {
            original = ((ColorDrawable) backgroundDrawable).getColor();
        } else {
            original = Color.TRANSPARENT;
        }
        ValueAnimator colorAnimator = ValueAnimator.ofObject(
                new ArgbEvaluator(),
                original,
                palette.getDarkVibrantColor(Color.BLACK)
        );
        colorAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });
        colorAnimator.start();
    }
}
