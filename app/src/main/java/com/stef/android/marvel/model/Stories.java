package com.stef.android.marvel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


/**
 * Created by Irene on 22-02-17.
 */
public class Stories implements Parcelable {
    public int available;
     public ArrayList<ItemS> items;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public ArrayList<ItemS> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemS> items) {
        this.items = items;
    }

    public static class ItemS implements Parcelable {

        public String resourceURI;
        public String name;
        public String type;

        public String getResourceURI() {
            return resourceURI;
        }

        public void setResourceURI(String resourceURI) {
            this.resourceURI = resourceURI;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.resourceURI);
            dest.writeString(this.name);
            dest.writeString(this.type);
        }

        public ItemS() {
        }

        protected ItemS(Parcel in) {
            this.resourceURI = in.readString();
            this.name = in.readString();
            this.type = in.readString();
        }

        public static final Parcelable.Creator<ItemS> CREATOR = new Parcelable.Creator<ItemS>() {
            @Override
            public ItemS createFromParcel(Parcel source) {
                return new ItemS(source);
            }

            @Override
            public ItemS[] newArray(int size) {
                return new ItemS[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.available);
        dest.writeTypedList(this.items);
    }

    public Stories() {
    }

    protected Stories(Parcel in) {
        this.available = in.readInt();
        this.items = in.createTypedArrayList(ItemS.CREATOR);
    }

    public static final Parcelable.Creator<Stories> CREATOR = new Parcelable.Creator<Stories>() {
        @Override
        public Stories createFromParcel(Parcel source) {
            return new Stories(source);
        }

        @Override
        public Stories[] newArray(int size) {
            return new Stories[size];
        }
    };
}
