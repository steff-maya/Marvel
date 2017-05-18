package com.stef.android.marvel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Irene on 21-02-17.
 */

public class Comics implements Parcelable {
    public int code;
    public String status;
    public Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Parcelable {

        public int offset;
        public int limit;
        public int total;
        public int count;
        public ArrayList<Results> results ;


        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public ArrayList<Results> getResults() {
            return results;
        }

        public void setResults(ArrayList<Results> results) {
            this.results = results;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.offset);
            dest.writeInt(this.limit);
            dest.writeInt(this.total);
            dest.writeInt(this.count);
            dest.writeTypedList(this.results);
        }

        public Data() {
        }

        protected Data(Parcel in) {
            this.offset = in.readInt();
            this.limit = in.readInt();
            this.total = in.readInt();
            this.count = in.readInt();
            this.results = in.createTypedArrayList(Results.CREATOR);
        }

        public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel source) {
                return new Data(source);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.status);
        dest.writeParcelable(this.data, flags);
    }

    public Comics() {
    }

    protected Comics(Parcel in) {
        this.code = in.readInt();
        this.status = in.readString();
        this.data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Parcelable.Creator<Comics> CREATOR = new Parcelable.Creator<Comics>() {
        @Override
        public Comics createFromParcel(Parcel source) {
            return new Comics(source);
        }

        @Override
        public Comics[] newArray(int size) {
            return new Comics[size];
        }
    };
}
