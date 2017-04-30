package it.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import it.Model.DailyMeal;
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

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView mealName;
        TextView chefName;
        TextView price;
        TextView numberGuests;
        ImageView imageMeal;
        RatingBar starRating;
        TextView address;

        public MenuViewHolder(View itemView) {
            super(itemView);
            mealName = (TextView)itemView.findViewById(R.id.mealName);
            imageMeal = (ImageView) itemView.findViewById(R.id.imageMeal);
            chefName = (TextView) itemView.findViewById(R.id.chefName);
            price = (TextView) itemView.findViewById(R.id.price);
            starRating = (RatingBar) itemView.findViewById(R.id.starRating);
            address = (TextView) itemView.findViewById(R.id.address);
            numberGuests = (TextView) itemView.findViewById(R.id.numberGuests);


        }
    }



    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.card_daily,parent,false);
        MenuViewHolder viewHolder = new MenuViewHolder(view);

        return viewHolder;

    }


    @Override
    public void onBindViewHolder(MenuViewHolder holder, final int position) {


        //int numberGuests = this.list.get(position).getMeal().getMaxPeople();

        float price = this.list.get(position).getMeal().getPrice();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        String priceString = formatter.format(price);

        String stars = this.list.get(position).getMeal().getRating();
        String urlPhoto = this.list.get(position).getMeal().getPhoto();

        //holder.numberGuests.setText(numberGuests);
        holder.mealName.setText(this.list.get(position).getMeal().getName());
        holder.price.setText(priceString);
        holder.chefName.setText(this.list.get(position).getMeal().getChef().getName());
        holder.starRating.setRating(Integer.parseInt(stars));
        holder.address.setText(this.list.get(position).getAddress().getNeighborhood());
        Picasso.with(context).load(urlPhoto).into(holder.imageMeal);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("daily",list.get(position));
                intent.setClass(context.getApplicationContext(), DetailActivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
