package com.github.pennyfive.finnkino.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 */
@Root(strict = false)
public class Event implements Parcelable {
    public static final String SIZE_PORTRAIT_MICRO = "EventMicroImagePortrait";
    public static final String SIZE_PORTRAIT_SMALL = "EventSmallImagePortrait";
    public static final String SIZE_PORTRAIT_LARGE = "EventLargeImagePortrait";
    public static final String SIZE_LANDSCAPE_SMALL = "EventSmallImageLandscape";
    public static final String SIZE_LANDSCAPE_LARGE = "EventLargeImageLandscape";

    @Element(name = "ID")
    private String id;
    @Element(name = "Title")
    private String title;
    @Element(name = "OriginalTitle")
    private String originalTitle;
    @Element(name = "ProductionYear")
    private int productionYear;
    @Element(name = "LengthInMinutes")
    private int lengthInMinutes;
    @Element(name = "Genres")
    private String genres;
    @Element(name = "ShortSynopsis")
    private String shortSynopsis;
    @Element(name = "Synopsis")
    private String synopsis;
    @Element(name = "Images")
    private Images images;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public String getGenres() {
        return genres;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getShortSynopsis() {
        return shortSynopsis;
    }

    public String getImageUrl(String size) {
        return images.getUrl(size);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeInt(productionYear);
        dest.writeInt(lengthInMinutes);
        dest.writeString(genres);
        dest.writeString(shortSynopsis);
        dest.writeString(synopsis);
        dest.writeParcelable(images, 0);
    }

    public static Parcelable.Creator<Event> CREATOR = new Creator<Event>() {

        @Override
        public Event createFromParcel(Parcel source) {
            Event event = new Event();
            event.id = source.readString();
            event.title = source.readString();
            event.originalTitle = source.readString();
            event.productionYear = source.readInt();
            event.lengthInMinutes = source.readInt();
            event.genres = source.readString();
            event.shortSynopsis = source.readString();
            event.synopsis = source.readString();
            event.images = source.readParcelable(Event.class.getClassLoader());
            return event;
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
