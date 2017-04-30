
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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

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
    private final String CHECKOUT = "http://appeatit.life/bt/checkout";
    private final int BRAINTREE_REQUEST_CODE = 4949;
    private final String URL_BOOKING =  "http://appeatit.life/Booking/New";

    private String clientToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        params = getIntent();
        dailyMeal = params.getParcelableExtra("daily");
        /**
         * Content Card
         * */
        TextView mealName, chefName, price, address;
        ImageView imageMeal;
        RatingBar starRating;

        mealName = (TextView) findViewById(R.id.mealName);
        chefName = (TextView) findViewById(R.id.chefName);
        price = (TextView) findViewById(R.id.price);
        address = (TextView) findViewById(R.id.address);
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
        address.setText(dailyMeal.getAddress().getNeighborhood());
        starRating.setRating(Integer.parseInt(dailyMeal.getMeal().getRating()));
        Picasso.with(imageMeal.getContext()).load(dailyMeal.getMeal().getPhoto()).into(imageMeal);

        getClientTokenFromServer();
        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle(R.string.attention);
                builder.setMessage(R.string.confirm_book);
                builder.setPositiveButton(R.string.yes, confirmBook);
                builder.setNegativeButton(R.string.no, null);
                builder.show();

            }
        });

    }
    DialogInterface.OnClickListener confirmBook = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            HashMap<String,String> paramsRequest = new HashMap<>();
            paramsRequest.put("idGuest","1");
            paramsRequest.put("idDaily",String.valueOf(dailyMeal.getId()));


            RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
            CustomObjectRequest request = new CustomObjectRequest(
                    Request.Method.POST,
                    URL_BOOKING,
                    paramsRequest,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("DEBUG",response.toString());
                            //     onBraintreeSubmit();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("DEBUG",error.getMessage());
                        }
                    }
            );


            rq.add(request);
        }
    };

    private void getClientTokenFromServer(){

        AsyncHttpClient androidClient = new AsyncHttpClient();

        Log.i(TAG, "ANDROID CLIENT: "+ androidClient.toString());

        androidClient.get(TOKEN_PATH, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, getString(R.string.token_failed) + responseString);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseToken) {
                Log.d(TAG, "Client token: " + responseToken);
                clientToken = responseToken;
            }
        });
    }
    public void spinnerLoad()
    {

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
        RequestParams params = new RequestParams("checkout", paymentNonce);
        params.put("payment_method_nonce", paymentNonce);
        AsyncHttpClient androidClient = new AsyncHttpClient();
        androidClient.post(CHECKOUT, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "Error: Failed to create a transaction");
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, "Output " + responseString);
            }
        });
    }
}