package app.eatit.appeatit;

import android.content.DialogInterface;
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

public class CadastrarActivity extends AppCompatActivity {

    private Button btnCadastrar;
    private EditText email,senha,nome;
    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        email = (EditText) findViewById(R.id.campoEmail);
        senha = (EditText) findViewById(R.id.campoSenha);
        nome = (EditText) findViewById(R.id.campoNome);
        rq = Volley.newRequestQueue(this);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }

    public void cadastrar(){
        if(email.getText().equals("") || senha.getText().equals("") || nome.getText().equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(CadastrarActivity.this);
            builder.setTitle("Atenção");
            builder.setMessage("Preencha todos os campos");
            builder.setNeutralButton("Ok",null);
            builder.show();
        }else {
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            HashMap<String, String> params = new HashMap<>();
            params.put("email", email.getText().toString());
            params.put("senha", senha.getText().toString());
            params.put("nome", nome.getText().toString());
            CustomObjectRequest request = new CustomObjectRequest(Request.Method.POST,
                    "http://acesolutions.com.br/Appeatit/cadastrar.php",
                    params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            findViewById(R.id.progressBar).setVisibility(View.GONE);
                            try {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CadastrarActivity.this);
                                builder.setTitle("Atenção");
                                builder.setMessage(response.getString("message"));
                                if (response.getBoolean("status")) {
                                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setClass(CadastrarActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    builder.show();

                                } else {
                                    builder.setNeutralButton("Ok", null);
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
            request.setTag("Cadastrar");
            rq.add(request);
        }
    }
}


