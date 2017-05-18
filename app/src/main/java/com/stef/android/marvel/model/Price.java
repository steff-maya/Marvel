package com.stef.android.marvel.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Irene on 21-02-17.
 */
public class Price implements Parcelable {
    public String type;
    public Double price;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeValue(this.price);
    }

    public Price() {
    }

    protected Price(Parcel in) {
        this.type = in.readString();
        this.price = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Price> CREATOR = new Parcelable.Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel source) {
            return new Price(source);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };
}