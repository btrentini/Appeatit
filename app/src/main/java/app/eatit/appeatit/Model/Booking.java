package app.eatit.appeatit.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Tobias on 04/07/2016.
 */
public class Booking implements Parcelable {

    private int id;
    private Refeicao refeicao;
    private User guest;
    private String data;

    protected Booking(Parcel in) {
        id = in.readInt();
        refeicao = in.readParcelable(Refeicao.class.getClassLoader());
        guest = in.readParcelable(User.class.getClassLoader());
        data = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(refeicao, flags);
        dest.writeParcelable(guest, flags);
        dest.writeString(data);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public Booking(){

    }



    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public Refeicao getRefeicao() {
        return refeicao;
    }

    public void setRefeicao(Refeicao refeicao) {
        this.refeicao = refeicao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
