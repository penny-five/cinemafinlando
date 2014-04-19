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
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Root(strict = false)
public class TheatreArea implements Parcelable {
    @Element(name = "ID")
    private String id;
    @Element(name = "Name")
    private String name;
    private final List<TheatreArea> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public String getName() {
        /* Individual theatres are returned from API in format "[City] :: [Theatre]". Don't want to use that format. */
        if (name.contains("::")) {
            return name.substring(name.indexOf("::") + 3, name.length());
        }
        return name;
    }

    public void addChildArea(TheatreArea child) {
        children.add(child);
    }

    public boolean isChildArea() {
        return name.contains("::");
    }

    @Override
    public String toString() {
        return "TheatreArea{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeList(children);
    }

    public static Creator<TheatreArea> CREATOR = new Creator<TheatreArea>() {
        @Override
        public TheatreArea createFromParcel(Parcel source) {
            TheatreArea area = new TheatreArea();
            area.id = source.readString();
            area.name = source.readString();
            source.readList(area.children, TheatreArea.class.getClassLoader());
            return area;
        }

        @Override
        public TheatreArea[] newArray(int size) {
            return new TheatreArea[size];
        }
    };
}
