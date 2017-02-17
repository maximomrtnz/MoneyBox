package com.maximomrtnz.moneybox.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Maxi on 2/16/2017.
 */

public class Movement implements Parcelable{

    private String category;
    private Date date;
    private Double amount;
    private String type;
    private String description;

    /**
     * Standard basic constructor for non-parcel
     * object creation
     */
    public Movement() {}

    /**
     *
     * Constructor to use when re-constructing object
     * from a parcel
     *
     * @param in a parcel from which to read this object
     */
    public Movement(Parcel in) {
        readFromParcel(in);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(category);
        parcel.writeString(description);
        parcel.writeString(type);
        parcel.writeDouble(amount);
        parcel.writeLong(date.getTime());

    }

    private void readFromParcel(Parcel parcel) {
        category = parcel.readString();
        description = parcel.readString();
        type = parcel.readString();
        amount = parcel.readDouble();
        date = new Date(parcel.readLong());
    }

    public static final Parcelable.Creator CREATOR =
        new Parcelable.Creator() {
            public Movement createFromParcel(Parcel in) {
                return new Movement(in);
            }

            public Movement[] newArray(int size) {
                return new Movement[size];
            }
    };
}
