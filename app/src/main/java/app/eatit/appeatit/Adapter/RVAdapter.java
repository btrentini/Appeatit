package app.eatit.appeatit.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.eatit.appeatit.Model.Chefe;
import app.eatit.appeatit.Model.Refeicao;
import app.eatit.appeatit.R;

/**
 * Created by Tuka on 13/06/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RefeicoesViewHolder>{

    private ArrayList<Chefe> chefes;
    private Context context;
    private LayoutInflater layoutInflater;

    public RVAdapter(ArrayList<Chefe> chefes,Context context){
        this.chefes = chefes;
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
        holder.nomeChefe.setText("Chefe :"+chefes.get(position).getNome());

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return chefes.size();
    }




    public static class RefeicoesViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView nomeChefe;

        public RefeicoesViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            nomeChefe = (TextView) itemView.findViewById(R.id.chefe);
        }
    }





}
