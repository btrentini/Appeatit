package app.eatit.appeatit.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuka on 10/06/2016.
 */
public class User implements Parcelable{

    private String nome, email;
    private  int id;
    private char tipo;
    private ArrayList<Refeicao> refeicoes;
    private ArrayList<CreditCard> creditCards;

    public User(){
        this.tipo = 'G';
    }
    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
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
        if(refeicoes == null){
            this.refeicoes = new ArrayList<>();
        }
        return refeicoes;
    }

    public void setRefeicoes(ArrayList<Refeicao> refeicoes) {

        if(refeicoes == null){
            this.refeicoes = new ArrayList<>();
        }
        this.refeicoes = refeicoes;
    }

    public ArrayList<CreditCard> getCreditCards() {
        if(creditCards== null){
            this.creditCards= new ArrayList<>();
        }
        return creditCards;
    }

    public List<String> getCardsName(){
        List<String> cards = new ArrayList<String>();
        for(CreditCard c : creditCards){
            cards.add(c.getFlag()+" - "+c.getCardNumber());
        }
        return cards;
    }

    public void setCreditCards(ArrayList<CreditCard> creditCards) {
        if(creditCards== null){
            this.creditCards= new ArrayList<>();
        }
        this.creditCards = creditCards;
    }

    public void addCard(CreditCard card){
        if(creditCards== null){
            this.creditCards= new ArrayList<>();
        }
        boolean exists = false;
        for(CreditCard c : this.creditCards){
            if(c.getId() == card.getId()){
                exists = true;
            }
        }
        if(exists == false){
            this.creditCards.add(card);
        }
    }


    protected User(Parcel in) {
        nome = in.readString();
        email = in.readString();
        id = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
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
