/*package app.eatit.appeatit;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.eatit.appeatit.DAO.CustomObjectRequest;
import app.eatit.appeatit.Model.Chefe;
import app.eatit.appeatit.Model.Refeicao;

public class MenuActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RequestQueue rq;
    private ArrayList<Chefe> chefes;
    private ArrayList<Refeicao> refeicoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreLista();
            }
        });
        rq = Volley.newRequestQueue(this);

    }

    public void abreLista() {
        chefes = new ArrayList<>();
        refeicoes = new ArrayList<>();
        CustomObjectRequest request = new CustomObjectRequest(
                Request.Method.POST,
                "http://www.acesolutions.com.br/Appeatit/refeicoes.php",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("status")){
                              //  Log.d("Log",""+response);
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
                                intent.setClass(MenuActivity.this, ListaActivity.class);
                                //intent.putParcelableArrayListExtra("chefes",chefes);
                                intent.putParcelableArrayListExtra("refeicoes",refeicoes);
                                startActivity(intent);
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                                builder.setTitle("Atenção");
                                builder.setMessage("Nenhum prato encontrado");
                                builder.setNeutralButton("Ok",null);
                                builder.show();
                            }

                        } catch (JSONException e) {
                            Log.d("Log", "Erro - "+e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        request.setTag("chefes");
        rq.add(request);
    }
}
*/