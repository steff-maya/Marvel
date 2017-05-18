package com.stef.android.marvel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Irene on 21-02-17.
 */
public class Results implements Parcelable {
    public String title;
    public int id;

    public String description;
    public String modified;

    public String format;
    public int pageCount;
    public ArrayList<Price> prices;
    public Creators creators;
    public Characters characters;
    public Stories stories;
    public Thumbnail thumbnail;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public ArrayList<Price> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<Price> prices) {
        this.prices = prices;
    }

    public Creators getCreators() {
        return creators;
    }

    public void setCreators(Creators creators) {
        this.creators = creators;
    }

    public Characters getCharacters() {
        return characters;
    }

    public void setCharacters(Characters characters) {
        this.characters = characters;
    }

    public Stories getStories() {
        return stories;
    }

    public void setStories(Stories stories) {
        this.stories = stories;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.id);
        dest.writeString(this.description);
        dest.writeString(this.modified);
        dest.writeString(this.format);
        dest.writeInt(this.pageCount);
        dest.writeTypedList(this.prices);
        dest.writeParcelable(this.creators, flags);
        dest.writeParcelable(this.characters, flags);
        dest.writeParcelable(this.stories, flags);
        dest.writeParcelable(this.thumbnail, flags);
    }

    public Results() {
    }

    protected Results(Parcel in) {
        this.title = in.readString();
        this.id = in.readInt();
        this.description = in.readString();
        this.modified = in.readString();
        this.format = in.readString();
        this.pageCount = in.readInt();
        this.prices = in.createTypedArrayList(Price.CREATOR);
        this.creators = in.readParcelable(Creators.class.getClassLoader());
        this.characters = in.readParcelable(Characters.class.getClassLoader());
        this.stories = in.readParcelable(Stories.class.getClassLoader());
        this.thumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
    }

    public static final Parcelable.Creator<Results> CREATOR = new Parcelable.Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel source) {
            return new Results(source);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };
}
