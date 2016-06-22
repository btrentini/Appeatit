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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.eatit.appeatit.DAO.CustomObjectRequest;
import app.eatit.appeatit.Model.Chefe;
import app.eatit.appeatit.Model.Refeicao;

public class MainActivity extends AppCompatActivity {

    private Button btnCadastrar,btnLogar;
    private EditText email, senha;
    private RequestQueue rq;
    private ArrayList<Chefe> chefes;
    private ArrayList<Refeicao> refeicoes;


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
        chefes = new ArrayList<>();
        refeicoes = new ArrayList<>();
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

                                JSONArray ja = response.getJSONArray("refeicoes");
                                for(int i = 0; i < ja.length(); i++){
                                    JSONObject jo = ja.getJSONObject(i);

                                    //Cria Chefes
                                    Chefe chefe = null;
                                    JSONObject joChefe = jo.getJSONObject("chefe");
                                    for (Chefe c : chefes){
                                        if(c.getId() == joChefe.getInt("id")){
                                            chefe = c;
                                            break;
                                        }
                                    }
                                    //Não achou nenhum chef na lista
                                    if(chefe == null){
                                        chefe = new Chefe();
                                        chefe.setId(joChefe.getInt("id"));
                                        chefe.setNome(joChefe.getString("nome"));
                                        chefes.add(chefe);
                                    }
                                    Refeicao refeicao;
                                    refeicao = new Refeicao(chefe);
                                    refeicao.setNome(jo.getString("nome"));
                                    refeicao.setDescricao(jo.getString("descricao"));
                                    refeicao.setValor((float)jo.getDouble("valor"));
                                    refeicao.setDiaSemana(jo.getInt("dia_semana"));
                                    refeicoes.add(refeicao);
                                }
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, ListaActivity.class);
                                //intent.putParcelableArrayListExtra("chefes",chefes);
                                intent.putParcelableArrayListExtra("refeicoes",refeicoes);
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
