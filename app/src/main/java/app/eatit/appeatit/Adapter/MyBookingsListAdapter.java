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
    private LayoutInflater layoutInflater;
    public MyBookingsListAdapter(Context context, ArrayList<Booking> bookings){
        this.context = context;
        this.bookings = bookings;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        holder.guest.setText(this.bookings.get(position).getGuest().getNome());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class MyBookingsViewHolder extends RecyclerView.ViewHolder {
        CardView cd;
        TextView guest;
        public MyBookingsViewHolder(View itemView) {
            super(itemView);
            //TODO My Bookings cards, and then recover the labels values
            cd = (CardView) itemView.findViewById(R.id.my_bookings_card);
        }
    }
}
