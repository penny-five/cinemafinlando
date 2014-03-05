package com.github.pennyfive.finnkino.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

/**
 *
 */
public class Images implements Parcelable {
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
