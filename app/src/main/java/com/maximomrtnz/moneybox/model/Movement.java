package com.maximomrtnz.moneybox.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.maximomrtnz.moneybox.commons.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Maxi on 2/16/2017.
 */

public class Movement implements Parcelable{

    private String category;
    private Long date;
    private Double amount;
    private String type;
    private String description;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
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

        parcel.writeString(id);
        parcel.writeString(category);
        parcel.writeString(description);
        parcel.writeString(type);
        parcel.writeDouble(amount);
        parcel.writeLong(date);

    }

    private void readFromParcel(Parcel parcel) {
        id = parcel.readString();
        category = parcel.readString();
        description = parcel.readString();
        type = parcel.readString();
        amount = parcel.readDouble();
        date = parcel.readLong();
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
