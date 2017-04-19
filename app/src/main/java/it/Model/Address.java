package it.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tobias on 06/04/2017.
 */

public class Address implements Parcelable {

    private int id;
    private String street,neighborhood, country, postal_code, state;
    private User user;

    public Address(){

    }

    protected Address(Parcel in) {
        id = in.readInt();
        street = in.readString();
        neighborhood = in.readString();
        country = in.readString();
        postal_code = in.readString();
        state = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(street);
        dest.writeString(neighborhood);
        dest.writeString(country);
        dest.writeString(postal_code);
        dest.writeString(state);
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
