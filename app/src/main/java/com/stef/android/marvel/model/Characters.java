package com.stef.android.marvel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Irene on 22-02-17.
 */
public class Characters implements Parcelable {
   public int  available;
   public ArrayList<ItemsE> items ;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public ArrayList<ItemsE> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemsE> items) {
        this.items = items;
    }

    public static class ItemsE implements Parcelable {
        public String resourceURI;
        public String name;

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.resourceURI);
            dest.writeString(this.name);
        }

        public ItemsE() {
        }

        protected ItemsE(Parcel in) {
            this.resourceURI = in.readString();
            this.name = in.readString();
        }

        public static final Parcelable.Creator<ItemsE> CREATOR = new Parcelable.Creator<ItemsE>() {
            @Override
            public ItemsE createFromParcel(Parcel source) {
                return new ItemsE(source);
            }

            @Override
            public ItemsE[] newArray(int size) {
                return new ItemsE[size];
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

    public Characters() {
    }

    protected Characters(Parcel in) {
        this.available = in.readInt();
        this.items = in.createTypedArrayList(ItemsE.CREATOR);
    }

    public static final Parcelable.Creator<Characters> CREATOR = new Parcelable.Creator<Characters>() {
        @Override
        public Characters createFromParcel(Parcel source) {
            return new Characters(source);
        }

        @Override
        public Characters[] newArray(int size) {
            return new Characters[size];
        }
    };
}
