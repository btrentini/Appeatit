package it.Adapters;

import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import it.appeatit.R;

/**
 * Created by Usuario on 24/03/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context context;
    private List<Object> list;
    private LayoutInflater layoutInflater;
    public MenuAdapter(List<Object> list, Context context){
        this.context = context;
        this.list = list;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.card_list,parent,false);
        MenuViewHolder viewHolder = new MenuViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        public MenuViewHolder(View itemView) {
            super(itemView);
        }
    }

}
