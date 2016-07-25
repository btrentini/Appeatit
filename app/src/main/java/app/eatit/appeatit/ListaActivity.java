package app.eatit.appeatit;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
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

import app.eatit.appeatit.Adapter.RecyclerClickListener;
import app.eatit.appeatit.Adapter.RefeicaoAdapter;
import app.eatit.appeatit.DAO.CustomObjectRequest;
import app.eatit.appeatit.Model.Refeicao;
import app.eatit.appeatit.Model.User;

public class ListaActivity extends AppCompatActivity {

    private RecyclerView rv;

    private ArrayList<Refeicao> refeicoes;
    private ArrayList<User> chefes;
    private Intent params;
    private ImageView imgBuscar;
    private RequestQueue rq;
    private EditText busca;
    private RefeicaoAdapter adapter;
    //Drawer
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setup drawer
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        //--------------------
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

                atualizarLista();
                return true;
            }
        });


        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:

                break;
            case R.id.nav_second_fragment:

                break;
            case R.id.nav_third_fragment:

                break;
            default:
                break;
        }
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    //METHODS
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
                                    User chefe = null;
                                    JSONObject joChefe = jo.getJSONObject("chefe");
                                    for (User c : chefes){
                                        if(c.getId() == joChefe.getInt("id")){
                                            chefe = c;
                                            break;
                                        }
                                    }
                                    //Não achou nenhum chef na lista
                                    if(chefe == null){
                                        chefe = new User();
                                        chefe.setTipo('C');
                                        chefe.setId(joChefe.getInt("id"));
                                        chefe.setNome(joChefe.getString("nome"));
                                        chefes.add(chefe);
                                    }
                                    Refeicao refeicao;
                                    refeicao = new Refeicao(chefe);
                                    refeicao.setId(jo.getInt("id"));
                                    refeicao.setNome(jo.getString("nome"));
                                    refeicao.setDescricao(jo.getString("descricao"));
                                    refeicao.setValor((float)jo.getDouble("valor"));
                                    refeicao.setDiaSemana(jo.getInt("dia_semana"));
                                    refeicoes.add(refeicao);
                                }
                                Log.d("Log","Oi");
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
