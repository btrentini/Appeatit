package app.eatit.appeatit;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.HashMap;

import app.eatit.appeatit.DAO.CustomObjectRequest;
import app.eatit.appeatit.Model.Booking;
import app.eatit.appeatit.Utils.DatabaseHelper;
import app.eatit.appeatit.Utils.GlobalData;

public class SelectCardActivity extends AppCompatActivity {

    private Booking booking;
    private Intent params;
    private Spinner spinner;
    private Button btnPay;
    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_card);

        params = getIntent();
        booking = params.getParcelableExtra("booking");

        rq = Volley.newRequestQueue(this);

        spinner = (Spinner) findViewById(R.id.spinnerCard);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, GlobalData.getInstance().getUser().getCardsName());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                pay();
            }
        });
    }

    private void pay() {
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        Log.d("Log", "Booking - "+booking.getData()+" Card Choose - "+spinner.getSelectedItemId());
        HashMap<String,String> paramsService = new HashMap<>();
        paramsService.put("id_user",String.valueOf(booking.getGuest().getId()));
        paramsService.put("id_meal",String.valueOf(booking.getRefeicao().getId()));
        paramsService.put("id_card",String.valueOf(GlobalData.getInstance().getUser().getCreditCards().get(spinner.getSelectedItemPosition()).getId()));
        paramsService.put("date",booking.getData());

        CustomObjectRequest request = new CustomObjectRequest(
                Request.Method.POST,
                DatabaseHelper.BOOKINGSERVICE,
                paramsService,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO Tratar resposta do servidor.
                        try {

                            if(response.getBoolean("status")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelectCardActivity.this);
                                builder.setTitle("Success");
                                builder.setMessage(response.getString("message"));
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent();
                                        intent.setClass(SelectCardActivity.this,ListaActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                findViewById(R.id.progressBar).setVisibility(View.GONE);
                                builder.show();
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelectCardActivity.this);
                                builder.setTitle("Fail");
                                builder.setMessage(response.getString("message"));
                                builder.setNeutralButton("Ok",null);
                                findViewById(R.id.progressBar).setVisibility(View.GONE);
                                builder.show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelectCardActivity.this);
                            builder.setTitle("Error");
                            builder.setMessage("Erro while making your book");
                            builder.setNeutralButton("Ok",null);
                            findViewById(R.id.progressBar).setVisibility(View.GONE);
                            builder.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SelectCardActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage("Error while making your book");
                        builder.setNeutralButton("Ok",null);
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        builder.show();
                    }
                }
        );

        request.setTag("pay");
        rq.add(request);
    }
}
