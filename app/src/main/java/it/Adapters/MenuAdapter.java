package it.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import it.Model.DailyMeal;
import it.Model.Meal;
import it.appeatit.DetailActivity;
import it.appeatit.R;

/**
 * Created by Usuario on 24/03/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context context;
    private List<DailyMeal> list;
    private LayoutInflater layoutInflater;

    public MenuAdapter(List<DailyMeal> list, Context context){
        this.context = context;
        this.list = list;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.card_daily,parent,false);
        MenuViewHolder viewHolder = new MenuViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, final int position) {

        holder.mealName.setText(this.list.get(position).getMeal().getName());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context.getApplicationContext(), DetailActivity.class);
                intent.putExtra("daily",list.get(position));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView mealName;
        TextView chefName;
        TextView price;
        ImageView mealPhoto;

        public MenuViewHolder(View itemView) {
            super(itemView);
            mealName = (TextView)itemView.findViewById(R.id.mealName);
            mealPhoto = (ImageView) itemView.findViewById(R.id.img);
            chefName = (TextView) itemView.findViewById(R.id.chefName);
            price = (TextView) itemView.findViewById(R.id.price);

        }
    }

}
