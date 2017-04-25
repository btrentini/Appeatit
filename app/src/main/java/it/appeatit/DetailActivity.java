package it.appeatit;

import android.content.DialogInterface;
import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //setupToolbar(((Toolbar)findViewById(R.id.toolbar)),"Meal Details");

        params = getIntent();
        dailyMeal = params.getParcelableExtra("daily");
        Log.d("DEBUG","Daily Street-> "+dailyMeal.getAddress().getStreet());

        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
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
            }
        });


    }
}
