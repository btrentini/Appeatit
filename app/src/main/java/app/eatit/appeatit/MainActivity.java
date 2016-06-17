package app.eatit.appeatit;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import app.eatit.appeatit.DAO.CustomObjectRequest;

public class MainActivity extends AppCompatActivity {

    private Button btnCadastrar,btnLogar;
    private EditText email, senha;
    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnLogar = (Button) findViewById(R.id.btnLogar);
        email = (EditText) findViewById(R.id.campoEmail);
        senha = (EditText) findViewById(R.id.campoSenha);
        rq = Volley.newRequestQueue(this);
        btnLogar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                logar();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CadastrarActivity.class);
                startActivity(intent);
            }
        });
    }

    public void logar(){
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        HashMap<String,String> params;
        params = new HashMap<>();
        params.put("email",email.getText().toString());
        params.put("senha",senha.getText().toString());
        CustomObjectRequest request = new CustomObjectRequest(Request.Method.POST,
                "http://acesolutions.com.br/Appeatit/logar.php",
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("status")){
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, MenuActivity.class);
                                findViewById(R.id.progressBar).setVisibility(View.GONE);
                                startActivity(intent);
                                finish();

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Atenção");
                                builder.setMessage(response.getString("message"));
                                builder.setNeutralButton("Ok",null);
                                findViewById(R.id.progressBar).setVisibility(View.GONE);
                                builder.show();
                            }
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
        request.setTag("Logar");
        rq.add(request);

    }
}
