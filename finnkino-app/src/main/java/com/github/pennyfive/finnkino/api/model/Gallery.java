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

package com.github.pennyfive.finnkino.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Root(strict = false)
public class Gallery implements Parcelable {
    @ElementList(entry = "GalleryImage", inline = true, required = false)
    private List<GalleryImage> images;

    public Gallery() {
    }

    private Gallery(Parcel in) {
        images = new ArrayList<>();
        in.readTypedList(images, GalleryImage.CREATOR);
    }

    public List<GalleryImage> getImages() {
        return images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(images);
    }

    public static Parcelable.Creator<Gallery> CREATOR = new Parcelable.Creator<Gallery>() {

        public Gallery createFromParcel(Parcel source) {
            return new Gallery(source);
        }

        public Gallery[] newArray(int size) {
            return new Gallery[size];
        }
    };
}
