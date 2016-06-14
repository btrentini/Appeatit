package app.eatit.appeatit.Model;

/**
 * Created by Tuka on 10/06/2016.
 */
public class Refeicao {

    private Chefe chefe;
    private int diaSemana;
    private String nome, descricao;



    private float valor;

    public Refeicao(Chefe chefe){
        chefe.getRefeicoes().add(this);
        this.chefe = chefe;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Chefe getChefe() {
        return chefe;
    }

    public void setChefe(Chefe chefe) {
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
}
