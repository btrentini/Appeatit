package app.eatit.appeatit;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

import app.eatit.appeatit.Adapter.RVAdapter;
import app.eatit.appeatit.Adapter.RecyclerClickListener;
import app.eatit.appeatit.Adapter.RefeicaoAdapter;
import app.eatit.appeatit.DAO.CustomObjectRequest;
import app.eatit.appeatit.Model.Chefe;
import app.eatit.appeatit.Model.Refeicao;

public class ListaActivity extends AppCompatActivity {

    private RecyclerView rv;

    private ArrayList<Refeicao> refeicoes;
    private ArrayList<Chefe> chefes;
    private Intent params;
    private ImageView imgBuscar;
    private RequestQueue rq;
    private EditText busca;
    private RefeicaoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        params = getIntent();
        //chefes = params.getParcelableArrayListExtra("chefes");
        refeicoes = params.getParcelableArrayListExtra("refeicoes");
        rq = Volley.newRequestQueue(this);

        busca = (EditText) findViewById(R.id.buscar);
        busca.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    atualizarLista();
                    return true;
                }
                return false;
            }
        });
        imgBuscar = (ImageView) findViewById(R.id.imgBuscar);
        imgBuscar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("Log","Oi");
                atualizarLista();
                return true;
            }
        });


        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        //RVAdapter adapter = new RVAdapter(chefes,this);
         adapter = new RefeicaoAdapter(refeicoes, this);
        rv.setAdapter(adapter);


        rv.addOnItemTouchListener(new RecyclerClickListener(this,new RecyclerClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(ListaActivity.this,DetalhesPratoActivity.class);
                intent.putExtra("refeicao",refeicoes.get(position));
                startActivity(intent);
            }
        }));



    }

    private void atualizarLista() {
        HashMap<String,String> paramsBusca = new HashMap<>();
        refeicoes = new ArrayList<>();
        chefes = new ArrayList<>();
        paramsBusca.put("busca",busca.getText().toString());
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        CustomObjectRequest request = new CustomObjectRequest(Request.Method.POST,
                "http://acesolutions.com.br/Appeatit/refeicoes.php",
                paramsBusca,
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
                                adapter.notifyDataSetChanged();

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ListaActivity.this);
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
        request.setTag("buscar");
        rq.add(request);
    }
}
