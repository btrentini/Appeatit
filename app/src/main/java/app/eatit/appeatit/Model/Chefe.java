package app.eatit.appeatit.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Tuka on 10/06/2016.
 */
public class Chefe implements Parcelable{

    private String nome, email;
    private  int id;
    private ArrayList<Refeicao> refeicoes;


    public Chefe(){
        this.refeicoes = new ArrayList<>();
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Refeicao> getRefeicoes() {
        return refeicoes;
    }

    public void setRefeicoes(ArrayList<Refeicao> refeicoes) {
        this.refeicoes = refeicoes;
    }

    protected Chefe(Parcel in) {
        nome = in.readString();
        email = in.readString();
        id = in.readInt();
    }

    public static final Creator<Chefe> CREATOR = new Creator<Chefe>() {
        @Override
        public Chefe createFromParcel(Parcel in) {
            return new Chefe(in);
        }

        @Override
        public Chefe[] newArray(int size) {
            return new Chefe[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(email);
        dest.writeInt(id);
    }
}
