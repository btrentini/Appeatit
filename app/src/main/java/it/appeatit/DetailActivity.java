package it.appeatit;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import it.Base.BaseActivity;
import it.DAO.CustomObjectRequest;
import it.Model.DailyMeal;
import it.Utils.Utils;

public class DetailActivity extends BaseActivity {

    private Intent params;
    private DailyMeal dailyMeal;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String PATH_TO_SERVER = "http://localhost:21287/bt/client_token";
    private String clientToken;
    private static final int BRAINTREE_REQUEST_CODE = 4949;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //setupToolbar(((Toolbar)findViewById(R.id.toolbar)),"Meal Details");

        params = getIntent();
        dailyMeal = params.getParcelableExtra("daily");
        Log.d("DEBUG","Daily Street-> "+dailyMeal.getAddress().getStreet());

        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);

        //BEFORE
        Log.i(TAG, "========== BEFORE");
        Log.i(TAG, "PATH: "+PATH_TO_SERVER);
        Log.i(TAG, "TOKEN: "+clientToken);
        Log.i(TAG, "REQUEST_CODE: "+BRAINTREE_REQUEST_CODE);



        btnConfirm.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                getClientTokenFromServer();

                //AFTER
                Log.i(TAG, "========== AFTER");
                Log.i(TAG, "PATH: "+PATH_TO_SERVER);
                Log.i(TAG, "TOKEN: "+clientToken);
                Log.i(TAG, "REQUEST_CODE: "+BRAINTREE_REQUEST_CODE);

                Log.i(TAG, "========== CLICOU!");
                onBraintreeSubmit(v);
              /*
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("Attention");
                builder.setMessage("Confirm your booking?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        HashMap<String,String> paramsRequest = new HashMap<>();
                        paramsRequest.put("idGuest","1");
                        paramsRequest.put("idGuest",String.valueOf(dailyMeal.getId()));
                        CustomObjectRequest request = new CustomObjectRequest(
                                Request.Method.POST,
                                "http://appeatit.life/Booking/New",
                                paramsRequest,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        try{
                                            if(response.getBoolean("status")){
                                                AlertDialog.Builder builderErro = new AlertDialog.Builder(DetailActivity.this);
                                                builderErro.setTitle("Attention");
                                                builderErro.setMessage("Book made succefully!");
                                                builderErro.setNeutralButton("Ok",null);
                                                builderErro.show();
                                            }else {
                                                AlertDialog.Builder builderErro = new AlertDialog.Builder(DetailActivity.this);
                                                builderErro.setTitle("Attention");
                                                builderErro.setMessage("Fail to book!");
                                                builderErro.setNeutralButton("Ok",null);
                                                builderErro.show();
                                            }
                                        }catch(JSONException e){
                                            AlertDialog.Builder builderErro = new AlertDialog.Builder(DetailActivity.this);
                                            builderErro.setTitle("Attention");
                                            builderErro.setMessage("Confirm your booking?");
                                            builderErro.setNeutralButton("Ok",null);
                                            builderErro.show();
                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }

                        );

                        RequestQueue rq = Volley.newRequestQueue(DetailActivity.this);
                        rq.add(request);
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.show();
                */
            }
        });

    }


    private void getClientTokenFromServer(){

        AsyncHttpClient androidClient = new AsyncHttpClient();

        Log.i(TAG, "ANDROID CLIENT: "+ androidClient.toString());

        androidClient.get(PATH_TO_SERVER, new TextHttpResponseHandler() {
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
    public void 
      (View view){
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
        RequestParams params = new RequestParams("NONCE", paymentNonce);
        AsyncHttpClient androidClient = new AsyncHttpClient();
        androidClient.post(PATH_TO_SERVER, params, new TextHttpResponseHandler() {
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
