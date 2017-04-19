package it.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import io.realm.annotations.PrimaryKey;

/**
 * Created by Tobias on 06/04/2017.
 */

public class DailyMeal implements Parcelable {

    @PrimaryKey
    private int id;
    private Meal meal;
    private Address address;
    private Date date;



    public DailyMeal(){

    }

    protected DailyMeal(Parcel in) {
        id = in.readInt();
        meal = in.readParcelable(Meal.class.getClassLoader());
        address = in.readParcelable(Address.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(meal, flags);
        dest.writeParcelable(address, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DailyMeal> CREATOR = new Creator<DailyMeal>() {
        @Override
        public DailyMeal createFromParcel(Parcel in) {
            return new DailyMeal(in);
        }

        @Override
        public DailyMeal[] newArray(int size) {
            return new DailyMeal[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
