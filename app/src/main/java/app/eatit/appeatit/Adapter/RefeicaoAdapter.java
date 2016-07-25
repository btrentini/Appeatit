package app.eatit.appeatit.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.eatit.appeatit.Model.Refeicao;
import app.eatit.appeatit.R;

/**
 * Created by Tuka on 15/06/2016.
 */
public class RefeicaoAdapter extends RecyclerView.Adapter<RefeicaoAdapter.RefeicoesViewHolder> {

    private ArrayList<Refeicao> refeicoes;
    private Context context;
    private LayoutInflater layoutInflater;

    public RefeicaoAdapter(ArrayList<Refeicao> refeicoes, Context context){
        this.refeicoes = refeicoes;
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public RefeicoesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.card_chefe,parent,false);
        RefeicoesViewHolder rvh = new RefeicoesViewHolder(view);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RefeicoesViewHolder holder, int position) {
        holder.nomeRefeicao.setText(this.refeicoes.get(position).getNome());
        holder.preco.setText("R$"+this.refeicoes.get(position).getValor());
        String desc = refeicoes.get(position).getDescricao();
        if(desc.length() > 44){
            desc = refeicoes.get(position).getDescricao().substring(0,45);
        }
        holder.descricao.setText(desc+"...");
        holder.chefe.setText(refeicoes.get(position).getChefe().getNome());
    }

    @Override
    public int getItemCount() {
        return this.refeicoes.size();
    }

    public static class RefeicoesViewHolder extends RecyclerView.ViewHolder/* implements View.OnClickListener*/ {
        CardView cv;
        TextView nomeRefeicao,preco,descricao,chefe;

        public RefeicoesViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            nomeRefeicao = (TextView) itemView.findViewById(R.id.refeicao);
            preco = (TextView) itemView.findViewById(R.id.preco);
            descricao = (TextView) itemView.findViewById(R.id.descricao);
            chefe= (TextView) itemView.findViewById(R.id.chefe);
           // cv.setOnClickListener(this);
        }



    }



}
