package it.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Tobias on 28/03/2017.
 */

public class Meal implements Parcelable {


    private User chef;
    private String name;
    private float price;
    private String photo;
    private String rating;
    private int maxPeople;

    @PrimaryKey
    private int id;

    public Meal(User chef){
        this.chef = chef;
    }
    public Meal(){

    }


    protected Meal(Parcel in) {
        chef = in.readParcelable(User.class.getClassLoader());
        name = in.readString();
        price = in.readFloat();
        id = in.readInt();
        photo = in.readString();
        rating = in.readString();
        maxPeople = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(chef, flags);
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeInt(id);
        dest.writeString(photo);
        dest.writeString(rating);
        dest.writeInt(maxPeople);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getChef() {
        return chef;
    }

    public void setChef(User chef) {
        this.chef = chef;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhoto(String photo) { this.photo = photo;}

    public String getPhoto(){return photo;}

    public void setRating(String rating) { this.rating = rating;}

    public String getRating(){return rating;}

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

}
