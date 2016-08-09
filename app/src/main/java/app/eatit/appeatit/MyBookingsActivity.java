package app.eatit.appeatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

import app.eatit.appeatit.Adapter.MyBookingsListAdapter;
import app.eatit.appeatit.Model.Booking;

public class MyBookingsActivity extends AppCompatActivity {

    private Intent params;
    private ArrayList<Booking> bookings;
    private RecyclerView rv;
    private MyBookingsListAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        params = getIntent();
        if(params.hasExtra("bookings")){
            bookings = params.getParcelableArrayListExtra("bookings");
        }
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        adapter = new MyBookingsListAdapter(this,bookings);
        rv.setAdapter(adapter);
        Log.i("Log","Param Booking - "+bookings);

    }
}
