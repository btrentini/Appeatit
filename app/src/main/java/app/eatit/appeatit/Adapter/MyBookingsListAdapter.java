package app.eatit.appeatit.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.eatit.appeatit.Model.Booking;
import app.eatit.appeatit.R;

/**
 * Created by Tobias on 25/07/2016.
 */
public class MyBookingsListAdapter extends RecyclerView.Adapter<MyBookingsListAdapter.MyBookingsViewHolder> {

    private Context context;
    private ArrayList<Booking> bookings;
    private ArrayList<Booking> guests;
    private LayoutInflater layoutInflater;
    private int guestsSize, bookingSize;

    //Constructor for CHEFS ONLY
    public MyBookingsListAdapter(Context context, ArrayList<Booking> bookings, ArrayList<Booking> guests){
        this.context = context;
        this.bookings = bookings;
        this.guests = guests;
        guestsSize = guests.size();
        bookingSize = bookings.size();
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Contructor for GUEST ONLY
    public MyBookingsListAdapter(Context context, ArrayList<Booking> bookings){
        this.context = context;
        this.bookings = bookings;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        guestsSize = 0;
        bookingSize = bookings.size();
    }

    @Override
    public MyBookingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO - Crate the view of Booking Card
        View view = layoutInflater.inflate(R.layout.booking_card,parent,false);
        MyBookingsViewHolder myBookingsViewHolder = new MyBookingsViewHolder(view);
        return myBookingsViewHolder;
    }

    @Override
    public void onBindViewHolder(MyBookingsViewHolder holder, int position) {
        if(guestsSize > 0 && (position+1) < guestsSize){
            //CHEF HAVE GUEST
            holder.person.setText("Guest - " + this.guests.get(position).getGuest().getNome());
            holder.mealName.setText(this.guests.get(position).getRefeicao().getNome());
            holder.mealDescr.setText(this.guests.get(position).getRefeicao().getDescricao());
            holder.date.setText(this.guests.get(position).getData());
            holder.status.setText(this.guests.get(position).getStatus());
        } else {
            holder.person.setText("Chef - "+this.bookings.get(position).getRefeicao().getChefe().getNome());
            holder.mealName.setText(this.bookings.get(position).getRefeicao().getNome());
            holder.mealDescr.setText(this.bookings.get(position).getRefeicao().getDescricao());
            holder.date.setText(this.bookings.get(position).getData());
            holder.status.setText(this.bookings.get(position).getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return bookingSize+guestsSize;
    }

    public static class MyBookingsViewHolder extends RecyclerView.ViewHolder {
        CardView cd;
        TextView person, mealName, mealDescr, date,status;
        public MyBookingsViewHolder(View itemView) {
            super(itemView);
            //TODO My Bookings cards, and then recover the labels values
            cd = (CardView) itemView.findViewById(R.id.my_bookings_card);
            person = (TextView) itemView.findViewById(R.id.person);
            mealName = (TextView) itemView.findViewById(R.id.mealName);
            mealDescr = (TextView) itemView.findViewById(R.id.mealDescr);
            date = (TextView) itemView.findViewById(R.id.date);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
