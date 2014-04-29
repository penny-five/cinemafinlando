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

package com.github.pennyfive.cinemafinlando.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

/**
 *
 */
public class Images implements Parcelable {
    public static final String SIZE_PORTRAIT_MICRO = "EventMicroImagePortrait";
    public static final String SIZE_PORTRAIT_SMALL = "EventSmallImagePortrait";
    public static final String SIZE_PORTRAIT_LARGE = "EventLargeImagePortrait";
    public static final String SIZE_LANDSCAPE_SMALL = "EventSmallImageLandscape";
    public static final String SIZE_LANDSCAPE_LARGE = "EventLargeImageLandscape";

    private final SparseArray<Object> values;

    public Images(SparseArray<Object> values) {
        this.values = values;
    }

    public String getUrl(String size) {
        return (String) values.get(size.hashCode());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSparseArray(values);
    }

    public static Creator CREATOR = new Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel source) {
            return new Images(source.readSparseArray(Images.class.getClassLoader()));
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
}