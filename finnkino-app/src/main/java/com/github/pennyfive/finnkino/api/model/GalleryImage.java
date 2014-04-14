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

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 */
@Root(strict = false)
public class GalleryImage implements Parcelable {
    @Element(name = "Title", required = false)
    private String title;
    @Element(name = "Location")
    private String url;
    @Element(name = "ThumbnailLocation")
    private String thumbnailUrl;

    public GalleryImage() {
    }

    private GalleryImage(Parcel in) {
        title = in.readString();
        url = in.readString();
        thumbnailUrl = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(thumbnailUrl);
    }

    public static Parcelable.Creator<GalleryImage> CREATOR = new Parcelable.Creator<GalleryImage>() {
        public GalleryImage createFromParcel(Parcel source) {
            return new GalleryImage(source);
        }

        public GalleryImage[] newArray(int size) {
            return new GalleryImage[size];
        }
    };
}
