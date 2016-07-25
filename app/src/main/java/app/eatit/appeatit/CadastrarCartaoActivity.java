package app.eatit.appeatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.eatit.appeatit.DAO.CustomObjectRequest;
import app.eatit.appeatit.Model.CreditCard;
import app.eatit.appeatit.Utils.DatabaseHelper;

public class CadastrarCartaoActivity extends AppCompatActivity {

    private CreditCard creditCard;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private List<String> flags;
    private EditText number, name, expires,securityCode, address, city, state, zipcode;
    //---
    private Intent params;
    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_cartao);

        creditCard = new CreditCard();
        params = getIntent();

        spinner = (Spinner) findViewById(R.id.spinnerCard);
        number = (EditText) findViewById(R.id.cardNumber);
        name = (EditText) findViewById(R.id.cardName);
        expires = (EditText) findViewById(R.id.expires);
        securityCode = (EditText) findViewById(R.id.securityCode);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        zipcode = (EditText) findViewById(R.id.zipcode);
        flags = new ArrayList<String>();
        flags.add("Visa");
        flags.add("Master -card");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,flags);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void insertCard(){
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        HashMap<String,String> paramsService = new HashMap<>();
        paramsService.put("card",spinner.getSelectedItem().toString());
        paramsService.put("cardNumber",number.getText().toString());
        paramsService.put("cardName",name.getText().toString());
        paramsService.put("expires",expires.getText().toString());
        paramsService.put("securityCode",securityCode.getText().toString());
        paramsService.put("address", address.getText().toString());
        paramsService.put("city",city.getText().toString());
        paramsService.put("state",state.getText().toString());
        paramsService.put("zipcode",zipcode.getText().toString());
        CustomObjectRequest request = new CustomObjectRequest(
                Request.Method.POST,
                DatabaseHelper.CARDSERVICE,
                paramsService,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //TODO terminar de tratar
                            if(response.getBoolean("status")){

}
                            findViewById(R.id.progressBar).setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        request.setTag("insertCard");
        rq.add(request);
    }
}
