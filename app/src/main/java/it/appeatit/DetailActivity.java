
package it.appeatit;

import com.android.volley.DefaultRetryPolicy;
import com.loopj.android.http.*;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import cz.msebera.android.httpclient.Header;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.HashMap;

import it.Base.BaseActivity;
import it.DAO.CustomObjectRequest;
import it.Model.DailyMeal;
import it.Utils.Utils;

public class DetailActivity extends BaseActivity {

    private Intent params;
    private DailyMeal dailyMeal;

    /** Constants */
    private final String TAG = MainActivity.class.getSimpleName();
    private final String TOKEN_PATH = "http://appeatit.life/bt/client_token";
    private final String CHECKOUT = "http://appeatit.life/bt/NONCE";
    private final int BRAINTREE_REQUEST_CODE = 4949;
    private final String URL_BOOKING =  "http://appeatit.life/Booking/New";

    private String clientToken;
    private int resultConfirm; //0 -> Error; 1->Success
    /** Volley */
    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        rq = Volley.newRequestQueue(getApplicationContext());
        params = getIntent();
        dailyMeal = params.getParcelableExtra("daily");
        /**
         * Content Card
         * */
        TextView mealName, chefName, price, address, numberGuests;
        ImageView imageMeal;
        RatingBar starRating;

        mealName = (TextView) findViewById(R.id.mealName);
        chefName = (TextView) findViewById(R.id.chefName);
        price = (TextView) findViewById(R.id.price);
        address = (TextView) findViewById(R.id.address);
        numberGuests = (TextView) findViewById(R.id.numberGuests);
        imageMeal = (ImageView) findViewById(R.id.imageMeal);
        starRating = (RatingBar) findViewById(R.id.starRating);

        float priceFloat = dailyMeal.getMeal().getPrice();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        String priceString = formatter.format(priceFloat);

        mealName.setText(dailyMeal.getMeal().getName());
        chefName.setText(dailyMeal.getMeal().getChef().getName());
        price.setText(priceString);

        int maxGuests = dailyMeal.getMeal().getMaxPeople();

        if(maxGuests > 1) {
            numberGuests.setText("Up to " + Integer.toString(maxGuests) + " guests");
        }else{
            numberGuests.setText("Up to 1 guest");
        }

        address.setText(dailyMeal.getAddress().getNeighborhood());
        starRating.setRating(Integer.parseInt(dailyMeal.getMeal().getRating()));
        Picasso.with(imageMeal.getContext()).load(dailyMeal.getMeal().getPhoto()).into(imageMeal);
        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                processBraintree();
            }
        });
    }


    private void processBraintree(){
        CustomObjectRequest request = new CustomObjectRequest(
                Request.Method.GET,
                TOKEN_PATH
                ,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, "Client token: " + response.getString("token"));
                            clientToken = response.getString("token");
                            onBraintreeSubmit();
                        }catch (JSONException e){
                            Log.d(TAG, "Erro Parse JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, getString(R.string.token_failed) + error.getMessage());
                    }
                }
        );
        rq.add(request);
    }

    public void onBraintreeSubmit(){
        DropInRequest dropInRequest = new DropInRequest().clientToken(clientToken);
        startActivityForResult(dropInRequest.getIntent(this), BRAINTREE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BRAINTREE_REQUEST_CODE){
            if (RESULT_OK == resultCode){
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String paymentNonce = result.getPaymentMethodNonce().getNonce();
                //send to your server
                Log.d(TAG, "Testing the app here");
                sendPaymentNonceToServer(paymentNonce);
            }else if(resultCode == Activity.RESULT_CANCELED){
                Log.d(TAG, "User cancelled payment");
            }else {
                Exception error = (Exception)data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d(TAG, " error exception");
            }
        }
    }
    private void sendPaymentNonceToServer(String paymentNonce){

        HashMap<String,String> paramsRequest = new HashMap<>();
        paramsRequest.put("payment_method_nonce", paymentNonce);
        paramsRequest.put("idGuest","1");
        paramsRequest.put("idDailyMeal",String.valueOf(dailyMeal.getId()));
        CustomObjectRequest request = new CustomObjectRequest(
                Request.Method.POST,
                CHECKOUT,
                paramsRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("status")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                                builder.setTitle(R.string.attention);
                                builder.setMessage(R.string.confirmed_pending_chef);
                                builder.setPositiveButton(R.string.ok, null);
                                resultConfirm = 1;
                                builder.show();
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                                builder.setTitle(R.string.attention);
                                builder.setMessage(R.string.error_transaction);
                                builder.setPositiveButton(R.string.ok, null);
                                resultConfirm = 0;
                                builder.show();
                            }

                        }catch(JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                            builder.setTitle(R.string.attention);
                            builder.setMessage(R.string.error_transaction);
                            builder.setPositiveButton(R.string.ok, null);
                            resultConfirm = 0;
                            builder.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: Failed to create a transaction");
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                        builder.setTitle(R.string.attention);
                        builder.setMessage(R.string.error_transaction);
                        builder.setPositiveButton(R.string.ok, null);
                        resultConfirm = 0;
                        builder.show();
                    }
                }
        );
        rq.add(request);

    }
}