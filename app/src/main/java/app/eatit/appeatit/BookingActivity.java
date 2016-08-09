package app.eatit.appeatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.eatit.appeatit.Model.Booking;

public class BookingActivity extends AppCompatActivity {

    private Booking booking;
    private Intent params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
    }
}
