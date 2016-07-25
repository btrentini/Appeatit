package app.eatit.appeatit.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tuka on 10/06/2016.
 */
public class Refeicao implements Parcelable {

    private User chefe;
    private int diaSemana;
    private String nome, descricao;
    private float valor;
    private int id;


    protected Refeicao(Parcel in) {
        chefe = in.readParcelable(User.class.getClassLoader());
        diaSemana = in.readInt();
        nome = in.readString();
        descricao = in.readString();
        valor = in.readFloat();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(chefe, flags);
        dest.writeInt(diaSemana);
        dest.writeString(nome);
        dest.writeString(descricao);
        dest.writeFloat(valor);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Refeicao> CREATOR = new Creator<Refeicao>() {
        @Override
        public Refeicao createFromParcel(Parcel in) {
            return new Refeicao(in);
        }

        @Override
        public Refeicao[] newArray(int size) {
            return new Refeicao[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public User getChefe() {
        return chefe;
    }

    public void setChefe(User chefe) {
        this.chefe = chefe;
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Refeicao(User chefe){
        chefe.getRefeicoes().add(this);
        this.chefe = chefe;
    }

}
