package app.eatit.appeatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import app.eatit.appeatit.Adapter.RVAdapter;
import app.eatit.appeatit.Model.Chefe;

public class ListaActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ArrayList<Chefe> chefes;
    private Intent params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        params = getIntent();
        chefes = params.getParcelableArrayListExtra("chefes");

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(chefes,this);
        rv.setAdapter(adapter);



    }
}
