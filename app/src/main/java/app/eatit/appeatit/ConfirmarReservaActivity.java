package app.eatit.appeatit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import app.eatit.appeatit.DAO.CustomObjectRequest;
import app.eatit.appeatit.Model.Booking;
import app.eatit.appeatit.Model.CreditCard;
import app.eatit.appeatit.Model.Refeicao;
import app.eatit.appeatit.Utils.DatabaseHelper;
import app.eatit.appeatit.Utils.GlobalData;

public class ConfirmarReservaActivity extends AppCompatActivity {

    private EditText date;
    private Button btnDialogCancel, btnDialogSelect,btnConfirm;
    private Dialog dialog;
    private Booking booking;
    private Refeicao refeicao;
    private Intent params;

    //----
    private RequestQueue rq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_reserva);

        params = getIntent();
        refeicao = params.getParcelableExtra("refeicao");
        rq = Volley.newRequestQueue(this);

        booking = new Booking();
        booking.setGuest(GlobalData.getInstance().getUser());
        booking.setRefeicao(refeicao);
        dialog = new Dialog(ConfirmarReservaActivity.this);
        dialog.setContentView(R.layout.dialog_date);
        dialog.setTitle(R.string.title_dialog_date);
        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        btnDialogCancel = (Button) dialog.findViewById(R.id.btnDialogCancel);
        btnDialogSelect = (Button) dialog.findViewById(R.id.btnDialogSelect);
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDialogSelect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               // Log.d("Log","Date Picker - "+datePicker.getDayOfMonth());
                String data = datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth();
                date.setText(data);
                //data = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                booking.setData(date.getText().toString());
                dialog.dismiss();
            }
        });

        date = (EditText) findViewById(R.id.date);
        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dialog.show();
                return true;
            }
        });

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        //TODO alertar aqui para confirmar a reserva
        btnConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                makeBook();
            }
        });
    }


    private void makeBook() {
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        HashMap<String,String> paramsService = new HashMap<>();
        paramsService.put("idUser",String.valueOf(booking.getGuest().getId()));
        paramsService.put("id_meal",String.valueOf(booking.getRefeicao().getId()));
        paramsService.put("date",booking.getData());

        CustomObjectRequest request = new CustomObjectRequest(
                Request.Method.POST,
                DatabaseHelper.BOOKINGSERVICE,
                paramsService,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO Tratar resposta do servidor.
                        Log.i("Log","Response - "+response);
                        try {

                            if(response.getBoolean("status")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmarReservaActivity.this);
                                builder.setTitle("Success");
                                builder.setMessage(response.getString("message"));
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent();
                                        intent.setClass(ConfirmarReservaActivity.this,ListaActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                findViewById(R.id.progressBar).setVisibility(View.GONE);
                                builder.show();
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmarReservaActivity.this);
                                builder.setTitle("Fail");
                                builder.setMessage(response.getString("message"));
                                builder.setNeutralButton("Ok",null);
                                findViewById(R.id.progressBar).setVisibility(View.GONE);
                                builder.show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmarReservaActivity.this);
                            builder.setTitle("Error");
                            builder.setMessage("Erro while making your book");
                            builder.setNeutralButton("Ok",null);
                            findViewById(R.id.progressBar).setVisibility(View.GONE);
                            e.printStackTrace();
                            builder.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmarReservaActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage("Erro while making your book");
                        builder.setNeutralButton("Ok",null);
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        builder.show();
                    }
                }
        );

        request.setTag("pay");
        rq.add(request);
    }
/*
    private void checkCreditCards(){
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        HashMap<String,String> paramsService = new HashMap<>();
        paramsService.put("email",GlobalData.getInstance().getUser().getEmail());
        CustomObjectRequest request = new CustomObjectRequest(
                Request.Method.POST,
                DatabaseHelper.CARDSERVICE,
                paramsService,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("status")){
                                CreditCard card;

                                JSONArray cardsArray = response.getJSONArray("cards");
                                for(int i = 0; i < cardsArray.length();i ++){
                                    JSONObject cardObject = cardsArray.getJSONObject(i);
                                    card = new CreditCard();
                                    card.setId(cardObject.getInt("id"));
                                    card.setCardName(cardObject.getString("card_name"));
                                    card.setCardNumber(cardObject.getString("card_number"));
                                    card.setExpires(cardObject.getString("expires"));
                                    card.setFlag(cardObject.getString("flag"));
                                    GlobalData.getInstance().getUser().addCard(card);
                                }
                                findViewById(R.id.progressBar).setVisibility(View.GONE);
                                Intent intent = new Intent();
                                //TODO Arrumar destino da intent.
                                intent.setClass(ConfirmarReservaActivity.this, SelectCardActivity.class);
                                intent.putExtra("booking",booking);
                                startActivity(intent);
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmarReservaActivity.this);
                                builder.setTitle("Ops");
                                builder.setMessage(response.getString("message"));
                                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent();
                                        intent.setClass(ConfirmarReservaActivity.this, CadastrarCartaoActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                builder.setNegativeButton(R.string.no, null);
                                findViewById(R.id.progressBar).setVisibility(View.GONE);
                                builder.show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmarReservaActivity.this);
                            builder.setTitle("Ops");
                            builder.setMessage(R.string.error_message);
                            builder.setNeutralButton("Ok", null);
                            findViewById(R.id.progressBar).setVisibility(View.GONE);
                            builder.show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmarReservaActivity.this);
                        builder.setTitle("Ops");
                        builder.setMessage(R.string.error_message);
                        builder.setNeutralButton("Ok", null);
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        builder.show();
                    }
                });
        request.setTag("checkCreditCards");
        rq.add(request);
    }
*/

}
