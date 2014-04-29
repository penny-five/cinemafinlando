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

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Root(strict = false)
public class DetailedEventGallery implements Parcelable {
    @ElementList(entry = "GalleryImage", inline = true, required = false)
    private List<Image> images;

    public DetailedEventGallery() {
    }

    private DetailedEventGallery(Parcel in) {
        images = new ArrayList<>();
        in.readTypedList(images, Image.CREATOR);
    }

    public List<Image> getImages() {
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

    public static Parcelable.Creator<DetailedEventGallery> CREATOR = new Parcelable.Creator<DetailedEventGallery>() {

        @Override
        public DetailedEventGallery createFromParcel(Parcel source) {
            return new DetailedEventGallery(source);
        }

        @Override
        public DetailedEventGallery[] newArray(int size) {
            return new DetailedEventGallery[size];
        }
    };

    /**
     *
     */
    @Root(strict = false)
    public static class Image implements Parcelable {
        @Element(name = "Title", required = false)
        private String title;
        @Element(name = "Location")
        private String url;
        @Element(name = "ThumbnailLocation")
        private String thumbnailUrl;

        public Image() {
        }

        private Image(Parcel in) {
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

        public static Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
            @Override
            public Image createFromParcel(Parcel source) {
                return new Image(source);
            }

            @Override
            public Image[] newArray(int size) {
                return new Image[size];
            }
        };
    }
}
