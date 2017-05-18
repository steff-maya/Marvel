package com.stef.android.marvel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Irene on 22-02-17.
 */
public class Creators implements Parcelable {
    public int available;
    public ArrayList<Items>items;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

    public static class Items implements Parcelable {

        public String resourceURI;
        public String name;
        public String role;

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

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.resourceURI);
            dest.writeString(this.name);
            dest.writeString(this.role);
        }

        public Items() {
        }

        protected Items(Parcel in) {
            this.resourceURI = in.readString();
            this.name = in.readString();
            this.role = in.readString();
        }

        public static final Parcelable.Creator<Items> CREATOR = new Parcelable.Creator<Items>() {
            @Override
            public Items createFromParcel(Parcel source) {
                return new Items(source);
            }

            @Override
            public Items[] newArray(int size) {
                return new Items[size];
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

    public Creators() {
    }

    protected Creators(Parcel in) {
        this.available = in.readInt();
        this.items = in.createTypedArrayList(Items.CREATOR);
    }

    public static final Parcelable.Creator<Creators> CREATOR = new Parcelable.Creator<Creators>() {
        @Override
        public Creators createFromParcel(Parcel source) {
            return new Creators(source);
        }

        @Override
        public Creators[] newArray(int size) {
            return new Creators[size];
        }
    };
}
